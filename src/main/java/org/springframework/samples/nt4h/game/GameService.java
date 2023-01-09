package org.springframework.samples.nt4h.game;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.javatuples.Triplet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.HealEnemy;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.exceptions.*;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final UserService userService;
    private final PlayerService playerService;
    private final HeroService heroService;
    private final ProductService productService;
    private final EnemyService enemyService;
    private final Advise advise;

    @Transactional(readOnly = true)
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Game> getAllGames(Pageable page) {
        return gameRepository.findAll(page);
    }

    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public Game getGameById(int id) {
        return gameRepository.findById(id).orElseThrow(() -> new NotFoundException("Game not found"));
    }

    @Transactional
    public void saveGame(Game game) {
        gameRepository.save(game);
    }


    @Transactional
    public void deleteGame(Game game) {
        game.onDeleteSetNull();
        for(Player player : game.getPlayers()) {
            User user = userService.getAllUsers().stream().filter(x->x.getBirthDate()==player.getBirthDate()).collect(Collectors.toList()).get(0);
            user.setGame(null);
            userService.saveUser(user);
        }
        game.setActualOrcs(null);
        gameRepository.save(game);
        gameRepository.delete(game);
    }

    @Transactional
    public void deleteGameById(int id) {
        Game game = getGameById(id);
        System.out.println("GameService.deleteGameById: " + game);
        deleteGame(game);
    }

    @Transactional(rollbackFor = {FullGameException.class, UserHasAlreadyAPlayerException.class})
    public Player addPlayerToGame(Game game, User user) throws FullGameException, UserHasAlreadyAPlayerException {
        if (game.getPlayers().contains(user.getPlayer()))
            return user.getPlayer();
        if (user.getPlayer() != null)
            throw new UserHasAlreadyAPlayerException();
        Player newPlayer = Player.createPlayer(user, game, false);
        playerService.createTurns(newPlayer);
        user.setPlayer(newPlayer);
        saveGame(game);
        advise.playerJoinGame(newPlayer, game);
        return newPlayer;
    }


    @Transactional(rollbackFor = {PlayerIsReadyException.class, RoleAlreadyChosenException.class, HeroAlreadyChosenException.class})
    public void addHeroToPlayer(Player player, HeroInGame heroInGame, Game game) throws RoleAlreadyChosenException, HeroAlreadyChosenException, PlayerIsReadyException {
        if (player.getReady())
            throw new PlayerIsReadyException();
        System.out.println("addHeroToPlayer " + player.getId());
        System.out.println("addHeroToPlayer " + heroInGame);
        Hero hero = heroService.getHeroById(heroInGame.getHero().getId());
        HeroInGame updatedHeroInGame = HeroInGame.createHeroInGame(hero, player); // TODO: revisar si es redundante.
        game.addPlayerWithNewHero(player, updatedHeroInGame);
        player.setReady(player.getHeroes().size() == game.getMode().getNumHeroes());
        playerService.addDeckFromRole(player, game.getMode());
        playerService.createTurns(player);
        saveGame(game);
        advise.choosenHero(player, heroInGame, game);
    }

    @Transactional(rollbackFor = FullGameException.class)
    public void createGame(User user, Game game) throws FullGameException {
        game = Game.createGame(game.getName(), game.getMode(),  game.getMaxPlayers(), game.getPassword());
        Player newPlayer = Player.createPlayer(user, game, true);;
        playerService.savePlayer(newPlayer);
        saveGame(game);
        userService.saveUser(user);
        List<EnemyInGame> orcsInGame = enemyService.addOrcsToGame(game.getMaxPlayers());
        game.setAllOrcsInGame(orcsInGame);
        game.getAllOrcsInGame().add(enemyService.addNightLordToGame());
        game.setActualOrcs(orcsInGame.subList(0, 3));
        productService.addProduct(game);
        advise.createGame(user, game);

    }

    @Transactional
    public void orderPlayer(List<Player> players, Game game) {
        List<Triplet<Integer, Player, Integer>> datos = Lists.newArrayList();
        // Calcula ataque de las dos primeras cartas en mano.
        for (var i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            List<AbilityInGame> abilities = player.getDeck().getInDeck();
            datos.add(new Triplet<>(i, player, abilities.get(0).getAttack() + abilities.get(1).getAttack()));
        }
        // Ordena por ataque.
        datos.sort((o1, o2) -> {
            int compare = o2.getValue2().compareTo(o1.getValue2());
            if (compare == 0)
                compare = o1.getValue1().getBirthDate().compareTo(o2.getValue1().getBirthDate());
            return compare;
        });
        players = datos.stream()
            .map(triplet -> {
                Player p = triplet.getValue1();
                p.setSequence(datos.indexOf(triplet));
                return p;
            }).collect(Collectors.toList());
        Player firstPlayer = players.get(0);
        game.setPlayers(players);
        game.setCurrentPlayer(firstPlayer);
        game.setCurrentTurn(firstPlayer.getTurn(Phase.START));
        saveGame(game);
        advise.playersOrdered(game);
    }

    @Transactional(rollbackFor = UserInAGameException.class)
    public void addSpectatorToGame(Game game, User user) throws UserInAGameException {
        game.getSpectators().add(user);
        if (user.getGame() != null)
            throw new UserInAGameException();
        user.setGame(game);
        userService.saveUser(user);
        saveGame(game);
        advise.spectatorJoinGame(user, game);
    }

    @Transactional
    public List<EnemyInGame> addNewEnemiesToBattle(List<EnemyInGame> enemies, List<EnemyInGame> allOrcs, Game game) {
        List<EnemyInGame> added = Lists.newArrayList();
        if (enemies.size() == 1 || enemies.size() == 2) {
            EnemyInGame enemy = allOrcs.get(1);
            enemies.add(enemy);
            added.add(enemy);
            allOrcs.remove(1);
        } else if (enemies.size() == 0) {
            List<EnemyInGame> newEnemies = game.getAllOrcsInGame().stream().limit(3).collect(Collectors.toList());
            added.addAll(newEnemies);
            allOrcs.removeAll(newEnemies);
        }
        saveGame(game);
        return added;
    }

    @Transactional
    public void restoreEnemyLife(List<EnemyInGame> enemies) {
        for (EnemyInGame enemyInGame : enemies) {
            Enemy enemy = enemyInGame.getEnemy();
            if (enemy.getHasCure()) {
                Action recoverEnemyLife = new HealEnemy(enemyInGame);
                recoverEnemyLife.executeAction();
            }
        }
    }

}
