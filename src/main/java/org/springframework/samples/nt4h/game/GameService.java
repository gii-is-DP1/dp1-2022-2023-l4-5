package org.springframework.samples.nt4h.game;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.javatuples.Triplet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.statistic.StatisticService;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
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
    private final StatisticService statisticService;
    private final DeckService deckService;
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
    public void deleteGameById(int id) {
        Game game = getGameById(id);
        game.onDeleteSetNull();
        gameRepository.save(game);
        gameRepository.delete(game);
    }

    @Transactional(rollbackFor = {FullGameException.class, UserHasAlreadyAPlayerException.class})
    public void addPlayerToGame(Game game, User user, String password) throws FullGameException, UserHasAlreadyAPlayerException, UserInAGameException, IncorrectPasswordException {
        if (user.getGame() != null && !user.getGame().equals(game))
            throw new UserInAGameException();
        if (!(Objects.equals(game.getPassword(), password) || game.getAccessibility() == Accessibility.PUBLIC))
            throw new IncorrectPasswordException();
        if (game.getPlayers().size() >= game.getMaxPlayers())
            throw new FullGameException();
        if (user.getPlayer() != null)
            throw new UserHasAlreadyAPlayerException();
        Player newPlayer = Player.createPlayer(user, game, false);
        playerService.createTurns(newPlayer);
        saveGame(game);
        user.setGame(game);
        user.setPlayer(newPlayer);
        userService.saveUser(user);
        advise.playerJoinGame();
    }

    @Transactional(rollbackFor = FullGameException.class)
    public void createGame(User user, Game game) throws FullGameException {
        game = Game.createGame(game.getName(), game.getMode(),  game.getMaxPlayers(), game.getPassword());
        Player newPlayer = Player.createPlayer(user, game, true);
        playerService.savePlayer(newPlayer);
        saveGame(game);
        userService.saveUser(user);
        List<EnemyInGame> orcsInGame = enemyService.addOrcsToGame(game.getMaxPlayers());
        game.setAllOrcsInGame(orcsInGame.subList(4, orcsInGame.size()));
        game.getAllOrcsInGame().add(0, enemyService.addNightLordToGame());
        game.setActualOrcs(orcsInGame.subList(0, 3));
        productService.addProduct(game);
        advise.createGame(game);
    }


    @Transactional(rollbackFor = {PlayerIsReadyException.class, RoleAlreadyChosenException.class, HeroAlreadyChosenException.class})
    public void addHeroToPlayer(Player player, HeroInGame heroInGame, Game game) throws RoleAlreadyChosenException, HeroAlreadyChosenException, PlayerIsReadyException {
        if (Boolean.TRUE.equals(player.getReady()))
            throw new PlayerIsReadyException();
        Hero hero = heroService.getHeroById(heroInGame.getHero().getId());
        HeroInGame updatedHeroInGame = HeroInGame.createHeroInGame(hero, player);
        game.addPlayerWithNewHero(player, updatedHeroInGame);
        player.setReady(player.getHeroes().size() == game.getMode().getNumHeroes());
        deckService.addDeckFromRole(player, game.getMode());
        playerService.createTurns(player);
        saveGame(game);
        advise.chosenHero(heroInGame);
    }

    @Transactional(rollbackFor = Exception.class)
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
        advise.spectatorJoinGame();
    }

    @Transactional
    public List<EnemyInGame> addNewEnemiesToBattle(Game game) {
        int numEnemies = 0;
        List<EnemyInGame> actualEnemies = game.getActualOrcs();
        List<EnemyInGame> allOrcsInGame = game.getAllOrcsInGame();
        if ((actualEnemies.size() == 2) || (actualEnemies.size() == 1))
            numEnemies = 1;
        else if (actualEnemies.size() == 0)
            numEnemies = 3;
        int addNighLord = actualEnemies.size() <= numEnemies ? 0 : 1;
        System.out.println("addNighLord: " + addNighLord);
        System.out.println("numEnemies: " + numEnemies);
        List<EnemyInGame> newEnemies = new ArrayList<>(allOrcsInGame.stream().skip(addNighLord).limit(numEnemies).collect(Collectors.toList()));
        allOrcsInGame.removeAll(newEnemies);
        actualEnemies.addAll(newEnemies);
        saveGame(game);
        return newEnemies;
    }

    @Transactional(rollbackFor = Exception.class)
    public void restoreEnemyLife(List<EnemyInGame> enemies) {
        for (EnemyInGame enemyInGame : enemies) {
            if (enemyInGame.getEnemy().getHasCure()) {
                enemyService.increaseLife(enemyInGame);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteKilledEnemy(List<EnemyInGame> attackedEnemies, Game game, Player player, Integer userId) {
        for(int e=0; e < attackedEnemies.size(); e++) {
            EnemyInGame enemy = attackedEnemies.get(e);
            if (enemy.getActualHealth() <= 0) {
                System.out.println("Enemy " + enemy.getEnemy().getName() + " killed");
                // statisticService.killedOrcs(player);
                statisticService.gainGold(player, enemy.getEnemy().getGold());
                statisticService.gainGlory(player, enemy.getEnemy().getGlory());
                statisticService.getNumDamageByUser(userId);
                statisticService.getNumOrcsByUser(userId);
                game.getActualOrcs().remove(enemy);
                playerService.savePlayer(player);
                saveGame(game);
            } else {
                playerService.savePlayer(player);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void attackEnemies(AbilityInGame usedAbility, Integer effectDamage, List<EnemyInGame> enemies, List<Integer> enemiesMoreDamage, Player player, Game game, Integer userId) {
        // TODO: quitar
        // if (usedAbility.getAttack() == 0)
        //    return;
        effectDamage += 999;
        Integer damageToEnemy = usedAbility.getAttack() + effectDamage;
        for (int e = 0; enemies.size() > e; e++) {
            EnemyInGame affectedEnemy = enemies.get(e);
            if (affectedEnemy.isNightLord())
                statisticService.gainGlory(player, 1);
            Integer extraDamage = enemiesMoreDamage.get(e);
            Integer initialEnemyHealth = affectedEnemy.getActualHealth();
            affectedEnemy.setActualHealth(initialEnemyHealth - (damageToEnemy + extraDamage));
            statisticService.damageDealt(player, (damageToEnemy + extraDamage));
            enemyService.saveEnemyInGame(affectedEnemy);
        }
        deleteKilledEnemy(enemies, game, player, userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer attackEnemyToActualPlayer(Game game, HttpSession session, Predicate<EnemyInGame> hasPreventedDamage, int defendedDmg, List<EnemyInGame> enemiesInATrap) {
        Player currentPlayer = game.getCurrentPlayer();
        if (game.getActualOrcs().isEmpty()) return 0;
        int damage = game.getActualOrcs().stream()
            .filter(hasPreventedDamage)
            .mapToInt(EnemyInGame::getActualHealth).sum();
        System.out.println("Damage: " + damage);
        int finalDamage = (damage >= defendedDmg) ? (damage - defendedDmg):damage;
        System.out.println("Final Damage: " + finalDamage);
        deckService.fromDeckToDiscard(currentPlayer, currentPlayer.getDeck(), damage);
        for (int i = 0; i < enemiesInATrap.size(); i++) {
            EnemyInGame enemyInGame = enemiesInATrap.get(i);
            enemyInGame.setActualHealth(0);
            enemyService.saveEnemyInGame(enemyInGame);
            game.getActualOrcs().remove(enemiesInATrap.get(i));
            saveGame(game);

        }
        return finalDamage;
    }

}
