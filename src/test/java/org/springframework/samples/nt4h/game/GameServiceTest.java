package org.springframework.samples.nt4h.game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.game.exceptions.PlayerInOtherGameException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceTest {
    @Autowired
    protected GameService gameService;
    @Autowired
    protected HeroService heroService;
    @BeforeAll
    void ini() throws PlayerInOtherGameException, HeroAlreadyChosenException, FullGameException {
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

            this.gameService.saveGame(game);
    }

    @Test
    public void findByIDTrue(){
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
        Game game = gameService.getGameByName("Prueba");
        assertNotNull(game);
        assertEquals(4,game.getMaxPlayers());
    }
    @Test
    public void findByNameFalse() {
        Game game = gameService.getGameByName("Prueba");
        assertNotNull(game);
        assertNotEquals(2,game.getMaxPlayers());
    }
    @Test
    public void findAll(){
        List<Game>ls= gameService.getAllGames();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(2,ls.size());
    }
    @Test
    public void shouldInsertGame() throws PlayerInOtherGameException, HeroAlreadyChosenException, FullGameException {
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
        game.setName("Prueba 1");
            this.gameService.saveGame(game);
        assertEquals(game,gameService.getGameByName("Prueba 1"));
    }
    @Test
    public void shouldUpdateGame() throws PlayerInOtherGameException, HeroAlreadyChosenException, FullGameException {
        Game game = gameService.getGameByName("Prueba");
        Phase newValue = Phase.ENEMY_ATTACK;
        game.setPhase(newValue);
            this.gameService.saveGame(game);
        assertEquals(newValue,gameService.getGameByName("Prueba").getPhase());
    }

    @Test
    public void shouldHeroAlreadyChosenException() {
        Game game = gameService.getGameByName("Prueba");
        Player player1= new Player();
        Player player2= new Player();
        HeroInGame h= new HeroInGame();
        h.setHero(heroService.getHeroByName("Aranel"));
        List<HeroInGame> hero = List.of(h);
        player1.setHeroes(hero);
        player2.setHeroes(hero);
        game.addPlayer(player1);
        game.addPlayer(player2);
        assertThrows(HeroAlreadyChosenException.class,()-> gameService.saveGame(game));
    }
    @Test
    public void shouldFullGameException(){
        Game game = gameService.getGameByName("Prueba");
        Player player1= new Player();
        Player player2= new Player();
        Player player3= new Player();
        Player player4= new Player();
        Player player5= new Player();
        List<Player> ls= List.of(player1,player2,player3,player4,player5);
        game.setPlayers(ls);
        assertThrows(FullGameException.class,()-> gameService.saveGame(game));
    }
    @Test
    public void deleteGameTest(){
        gameService.deleteGameById(1);
        assertFalse(gameService.gameExists(1));
    }
}
