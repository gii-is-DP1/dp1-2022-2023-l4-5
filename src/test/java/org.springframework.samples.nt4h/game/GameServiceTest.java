
package org.springframework.samples.nt4h.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceTest {
    @Autowired
    protected GameService gameService;
    @Autowired
    protected UserService userService;


        @BeforeAll
        void ini () throws HeroAlreadyChosenException, FullGameException {
            Game game = new Game();
            game.setAccessibility(Accessibility.PUBLIC);
            Player player = new Player();
            game.addPlayer(player);
            game.setMaxPlayers(4);
            game.setMode(Mode.UNI_CLASS);
            game.setHasStages(false);
            List<EnemyInGame> passive = new ArrayList();
            game.setPassiveOrcs(passive);
            game.setPassword("");
            game.setName("Prueba");
            gameService.saveGame(game);
        }

        @Test
        public void findByIDTrue () {
            Game game = this.gameService.getGameById(1);
            Assertions.assertNotNull(game);
            assertEquals(4, game.getMaxPlayers());
        }

        @Test
        public void findByIDFalse () {
            Game game = this.gameService.getGameById(1);
            Assertions.assertNotNull(game);
            Assertions.assertNotEquals("PruebaXX", game.getName());
        }

        @Test
        public void findAll () {
            List<Game> ls = this.gameService.getAllGames();
            Assertions.assertNotNull(ls);
            Assertions.assertFalse(ls.isEmpty());
            assertEquals(1, ls.size());
        }

        @Test
        public void shouldInsertGame () throws HeroAlreadyChosenException, FullGameException {
            Game game = new Game();
            game.setAccessibility(Accessibility.PUBLIC);
            Player player = new Player();
            List<Player> players = List.of(player);
            game.setPlayers(players);
            game.setMaxPlayers(4);
            game.setMode(Mode.UNI_CLASS);
            game.setHasStages(false);
            List<EnemyInGame> passive = new ArrayList();
            game.setPassiveOrcs(passive);
            game.setPassword("");
            game.setName("Prueba 1");
            this.gameService.saveGame(game);
            assertEquals(game, this.gameService.getGameById(3));
            gameService.deleteGameById(3);
        }

        @Test
        public void shouldUpdateGame () throws HeroAlreadyChosenException, FullGameException {
            Game game = this.gameService.getGameById(1);
            Phase newValue = Phase.ENEMY_ATTACK;
            game.setPhase(newValue);
            this.gameService.saveGame(game);
            assertEquals(newValue, this.gameService.getGameById(1).getPhase());
        }
        @Test
        public void addPlayerTest() throws FullGameException {
            User user =userService.getUserById(1);
            Player player = new Player();
            Game game = gameService.getGameById(1);
            gameService.addPlayerToGame(player,game,user);
            assertEquals(1,gameService.getGameById(1).getPlayers().size());
        }
        @Test
        public void createGameTest() throws FullGameException {
            User user =userService.getUserById(1);
            Game game = new Game();
            game.setPassword("");
            game.setMaxPlayers(4);
            game.setMode(Mode.UNI_CLASS);
            gameService.createGame(user,game);
            Boolean nombresIguales =gameService.getGameById(2).getPlayers().stream()
                .anyMatch(x->x.getName().equals(user.getUsername()));
            assertTrue(nombresIguales);
            gameService.deleteGameById(2);
        }

        @AfterAll
        @Test
        public void deleteGameTest () {
            gameService.deleteGameById(1);
            Assertions.assertFalse(this.gameService.existsGameById(1));
        }
    }
