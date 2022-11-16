package org.springframework.samples.nt4h.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.effect.Phase;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTest {
    @Autowired
    protected GameService gameService;
    /*
    void ini(){
        Game game= new Game();
        game.setAccessibility(Accessibility.PUBLIC);
        Player player= new Player();
        List<Player> players= List.of(player);
        game.setPlayers(players);
        game.setMaxPlayers(4);
        game.setMode(Mode.UNI_CLASS);
        game.setHasStages(false);
        List<EnemyInGame> passive=new ArrayList<EnemyInGame>();
        game.setPassiveOrcs(passive);
        game.setPassword("");
        game.setName("Prueba");
        gameService.saveGame(game);


    }

    @Test
    public void findByIDTrue(){
        ini();
        Game game = gameService.getGameById(1);
        assertNotNull(game);
        assertEquals(4,game.getMaxPlayers() );
    }
    @Test
    public void findByIDFalse() {
        Game game= gameService.getGameById(1);
        assertNotNull(game);
        assertNotEquals("PruebaXX",game.getName());
    }
    @Test
    public void findByNameTrue() {
        ini();
        Game game = gameService.getGameByName("Prueba");
        assertNotNull(game);
        assertEquals(4,game.getMaxPlayers());
    }
    @Test
    public void findByNameFalse() {
        ini();
        Game game = gameService.getGameByName("Prueba");
        assertNotNull(game);
        assertNotEquals(2,game.getMaxPlayers());
    }
    @Test
    public void findAll(){
        ini();
        List<Game>ls= gameService.getAllGames();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(2,ls.size());
    }
    @Test
    public void shouldInsertGame() {
        Game game= new Game();
        game.setAccessibility(Accessibility.PUBLIC);
        Player player= new Player();
        List<Player> players= List.of(player);
        game.setPlayers(players);
        game.setMaxPlayers(3);
        game.setMode(Mode.UNI_CLASS);
        game.setHasStages(false);
        List<EnemyInGame> passive=new ArrayList<EnemyInGame>();
        game.setPassiveOrcs(passive);
        game.setName("Prueba");
        this.gameService.saveGame(game);
        assertEquals(gameService.getGameByName("Prueba"),game);

    }
    @Test
    public void shouldUpdateGame() {
        ini();
        Game game = gameService.getGameByName("Prueba");
        Phase newValue = Phase.ENEMY_ATTACK;
        game.setPhase(newValue);
        this.gameService.saveGame(game);
        assertEquals(newValue,gameService.getGameByName("Prueba").getPhase());
    }

     */
}
