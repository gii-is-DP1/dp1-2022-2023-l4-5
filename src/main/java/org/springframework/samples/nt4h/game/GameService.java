package org.springframework.samples.nt4h.game;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.javatuples.Triplet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.exceptions.*;
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

    @Transactional(readOnly = true)
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Game> getAllGames(Pageable page) {
        return gameRepository.findAll(page);
    }

    @Transactional(readOnly = true)
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
        gameRepository.save(game);
        gameRepository.delete(game);
    }

    @Transactional
    public void deleteGameById(int id) {
        Game game = getGameById(id);
        deleteGame(game);
    }

    @Transactional(rollbackFor = {FullGameException.class, HeroAlreadyChosenException.class})
    public Player addPlayerToGame(Game game, User user) throws FullGameException, UserHasAlreadyAPlayerException {
        if (game.getPlayers().contains(user.getPlayer()))
            return user.getPlayer();
        if (user.getPlayer() != null)
            throw new UserHasAlreadyAPlayerException();
        Player newPlayer = Player.createPlayer(user, game, false);
        playerService.createTurns(newPlayer);
        user.setPlayer(newPlayer);
        saveGame(game);
        return newPlayer;
    }


    @Transactional(rollbackFor = {FullGameException.class, HeroAlreadyChosenException.class, PlayerIsReadyException.class})
    public void addHeroToPlayer(Player player, HeroInGame heroInGame, Game game) throws RoleAlreadyChosenException, HeroAlreadyChosenException, PlayerIsReadyException {
        if (player.getReady())
            throw new PlayerIsReadyException();
        Hero hero = heroService.getHeroById(heroInGame.getHero().getId());
        HeroInGame updatedHeroInGame = HeroInGame.createHeroInGame(hero, player); // TODO: revisar si es redundante.
        game.addPlayerWithNewHero(player, updatedHeroInGame);
        player.setReady(player.getHeroes().size() == game.getMode().getNumHeroes());
        playerService.addDeckFromRole(player, game.getMode());
        playerService.createTurns(player);
        saveGame(game);
    }

    @Transactional(rollbackFor = {FullGameException.class})
    public void createGame(User user, Game game) throws FullGameException {
        game = Game.createGame(game.getName(), game.getMode(),  game.getMaxPlayers(), game.getPassword());
        Player newPlayer = Player.createPlayer(user, game, true);;
        playerService.savePlayer(newPlayer);
        saveGame(game);
        userService.saveUser(user);
        List<EnemyInGame> orcsInGame = enemyService.addOrcsToGame();
        game.setAllOrcsInGame(orcsInGame);
        game.getAllOrcsInGame().add(enemyService.addNightLordToGame());
        game.setActualOrcs(orcsInGame.subList(0, 3));
        productService.addProduct(game);

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
    }

    @Transactional(rollbackFor = {UserInAGameException.class})
    public void addSpectatorToGame(Game game, User user) throws UserInAGameException {
        if (user.getGame() == null) {
            game.getSpectators().add(user);
            user.setGame(game);
            userService.saveUser(user);
            saveGame(game);
        }
        if (user.getGame() != game)
            throw new UserInAGameException();
    }
}
