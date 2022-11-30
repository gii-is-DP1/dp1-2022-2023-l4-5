package org.springframework.samples.nt4h.game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameTest {
    @Autowired
    public HeroService heroService;

    public Game game;

    @BeforeEach
    void ini() throws HeroAlreadyChosenException, FullGameException {
        game= new Game();
        game.setAccessibility(Accessibility.PUBLIC);
        game.setMaxPlayers(4);
        game.setMode(Mode.UNI_CLASS);
        game.setHasStages(false);
        List<EnemyInGame> passive=new ArrayList<EnemyInGame>();
        game.setPassiveOrcs(passive);
        game.setPassword("");
        game.setName("Prueba");
    }
    @Test
    public void shouldHeroAlreadyChosenException() throws FullGameException, RoleAlreadyChosenException, HeroAlreadyChosenException {
        Player player1= new Player();
        Player player2= new Player();
        HeroInGame h= new HeroInGame();
        h.setHero(heroService.getHeroByName("Aranel"));
        game.addPlayerWithNewHero(player1,h);
        assertThrows(HeroAlreadyChosenException.class,()-> game.addPlayerWithNewHero(player2,h));
    }

    @Test
    public void shouldFullGameException() throws FullGameException {
        Player player1= new Player();
        Player player2= new Player();
        Player player3= new Player();
        Player player4= new Player();
        Player player5= new Player();
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);
        assertThrows(FullGameException.class,()-> this.game.addPlayer(player5));
    }
}
