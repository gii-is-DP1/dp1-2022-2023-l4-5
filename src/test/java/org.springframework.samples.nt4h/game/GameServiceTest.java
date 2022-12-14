
package org.springframework.samples.nt4h.game;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Service;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
@TestInstance(Lifecycle.PER_CLASS)
public class GameServiceTest {
    @Autowired
    protected GameService gameService;
    @Autowired
    protected HeroService heroService;

    public GameServiceTest() {
    }

    @BeforeAll
    void ini() throws HeroAlreadyChosenException, FullGameException {
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
        game.setName("Prueba");
        this.gameService.saveGame(game);
    }

    @Test
    public void findByIDTrue() {
        Game game = this.gameService.getGameById(1);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(4, game.getMaxPlayers());
    }

    @Test
    public void findByIDFalse() {
        Game game = this.gameService.getGameById(1);
        Assertions.assertNotNull(game);
        Assertions.assertNotEquals("PruebaXX", game.getName());
    }

    @Test
    public void findByNameTrue() {
        Game game = this.gameService.getGameByName("Prueba");
        Assertions.assertNotNull(game);
        Assertions.assertEquals(4, game.getMaxPlayers());
    }

    @Test
    public void findByNameFalse() {
        Game game = this.gameService.getGameByName("Prueba");
        Assertions.assertNotNull(game);
        Assertions.assertNotEquals(2, game.getMaxPlayers());
    }

    @Test
    public void findAll() {
        List<Game> ls = this.gameService.getAllGames();
        Assertions.assertNotNull(ls);
        Assertions.assertFalse(ls.isEmpty());
        Assertions.assertEquals(2, ls.size());
    }

    @Test
    public void shouldInsertGame() throws HeroAlreadyChosenException, FullGameException {
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
        Assertions.assertEquals(game, this.gameService.getGameByName("Prueba 1"));
    }

    @Test
    public void shouldUpdateGame() throws HeroAlreadyChosenException, FullGameException {
        Game game = this.gameService.getGameByName("Prueba");
        Phase newValue = Phase.ENEMY_ATTACK;
        game.setPhase(newValue);
        this.gameService.saveGame(game);
        Assertions.assertEquals(newValue, this.gameService.getGameByName("Prueba").getPhase());
    }

    @Test
    public void deleteGameTest() {
        this.gameService.deleteGameById(1);
        Assertions.assertFalse(this.gameService.gameExists(1));
    }
}
