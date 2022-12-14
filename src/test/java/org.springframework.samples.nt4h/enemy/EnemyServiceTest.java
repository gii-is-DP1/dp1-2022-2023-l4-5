//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.springframework.samples.nt4h.game;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.stereotype.Service;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
@TestInstance(Lifecycle.PER_CLASS)
public class GameTest {
    @Autowired
    public HeroService heroService;
    public Game game;

    public GameTest() {
    }

    @BeforeEach
    void ini() throws HeroAlreadyChosenException, FullGameException {
        this.game = new Game();
        this.game.setAccessibility(Accessibility.PUBLIC);
        this.game.setMaxPlayers(4);
        this.game.setMode(Mode.UNI_CLASS);
        this.game.setHasStages(false);
        List<EnemyInGame> passive = new ArrayList();
        this.game.setPassiveOrcs(passive);
        this.game.setPassword("");
        this.game.setName("Prueba");
    }

    @Test
    public void shouldHeroAlreadyChosenException() throws FullGameException, RoleAlreadyChosenException, HeroAlreadyChosenException {
        Player player1 = new Player();
        Player player2 = new Player();
        HeroInGame h = new HeroInGame();
        h.setHero(this.heroService.getHeroByName("Aranel"));
        this.game.addPlayerWithNewHero(player1, h);
        Assertions.assertThrows(HeroAlreadyChosenException.class, () -> {
            this.game.addPlayerWithNewHero(player2, h);
        });
    }

    @Test
    public void shouldFullGameException() throws FullGameException {
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();
        Player player5 = new Player();
        this.game.addPlayer(player1);
        this.game.addPlayer(player2);
        this.game.addPlayer(player3);
        this.game.addPlayer(player4);
        Assertions.assertThrows(FullGameException.class, () -> {
            this.game.addPlayer(player5);
        });
    }
}
