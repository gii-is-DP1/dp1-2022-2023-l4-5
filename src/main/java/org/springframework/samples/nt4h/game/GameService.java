package org.springframework.samples.nt4h.game;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.javatuples.Triplet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Iterator;
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
    // Obtener las partidas de cinco en cinco.
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
        gameRepository.delete(game);
    }

    @Transactional
    public void deleteGameById(int id) {
        gameRepository.deleteById(id);
    }

    public Player addPlayerToGame(Player player, Game game, User user) throws FullGameException {
        Player newPlayer = player.toBuilder().host(false).birthDate(user.getBirthDate()).game(game).build();
        newPlayer.setName(user.getUsername());
        game.addPlayer(newPlayer);
        playerService.savePlayerAndCreateTurns(newPlayer);
        user.setPlayer(newPlayer);
        userService.saveUser(user);
        saveGame(game);
        return newPlayer;
    }

    @Transactional
    public void addHeroToPlayer(Player player, HeroInGame heroInGame, Game game) throws RoleAlreadyChosenException, HeroAlreadyChosenException, FullGameException {
        game.addPlayerWithNewHero(player, heroInGame);
        player.setReady(player.getHeroes().size() == game.getMode().getNumHeroes());
        heroService.saveHeroInGame(heroInGame);
        playerService.addDeckFromRole(player, game.getMode());
        playerService.savePlayerAndCreateTurns(player);
        saveGame(game);
    }

    @Transactional
    public void createGame(User user, Game game) throws FullGameException {
        // TODO: Crear un método a parte para creación de player.
        Player newPlayer = Player.builder().host(true).glory(0).gold(0).ready(false).nextPhase(Phase.EVADE).damageDealt(0).damageDealtToNightLords(0)
            .birthDate(user.getBirthDate()).damageProtect(0).numOrcsKilled(0).numWarLordKilled(0).sequence(-1).wounds(0).game(game).build();
        newPlayer.setName(user.getUsername());
        user.setGame(game);
        user.setPlayer(newPlayer);
        game.setStartDate(LocalDateTime.now());
        game.addPlayer(newPlayer);
        game.setAccessibility(game.getPassword().isEmpty() ? Accessibility.PUBLIC : Accessibility.PRIVATE);
        saveGame(game);
        List<EnemyInGame> orcsInGame = enemyService.addOrcsToGame();
        EnemyInGame nightLordInGame = enemyService.addNightLordToGame();
        game.setAllOrcsInGame(orcsInGame);
        game.addOrcsInGame(nightLordInGame);
        game.setActualOrcs(orcsInGame.subList(0, 3));
        productService.addProduct(game);

    }

    // user.getFriends().sublist(pageable.getOffset(), pageable.getOffset() + pageable.getPageSize())

    @Transactional
    public void orderPlayer(List<Player> players, Game game) {
        System.out.println("Ordering players");
        List<Triplet<Integer, Player, Integer>> datos = Lists.newArrayList();
        for (var i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            List<AbilityInGame> abilities = player.getInDeck();
            datos.add(new Triplet<>(i, player, abilities.get(0).getAttack() + abilities.get(1).getAttack()));
        }
        datos.sort((o1, o2) -> o2.getValue2().compareTo(o1.getValue2()));
        System.out.println("datos = " + datos);
        if (Objects.equals(datos.get(0).getValue2(), datos.get(1).getValue2()) &&
            datos.get(0).getValue1().getBirthDate().isAfter(datos.get(1).getValue1().getBirthDate())) {
            var first = datos.get(0);
            var second = datos.get(1);
            datos.set(0, second);
            datos.set(1, first);
        }
        players = datos.stream()
            .map(triplet -> {
                Player p = triplet.getValue1();
                p.setSequence(triplet.getValue0());
                return p;
            }).collect(Collectors.toList());
        Player firstPlayer = players.get(0);
        players.forEach(playerService::savePlayer);
        System.out.println("First player: " + firstPlayer.getSequence());
        game.setPlayers(players);
        game.setCurrentPlayer(firstPlayer);
        game.setCurrentTurn(firstPlayer.getTurn(Phase.EVADE));
        saveGame(game);
    }

    @Transactional(readOnly = true)
    public boolean existsGameById(int id) {
        return gameRepository.existsById(id);
    }
}
