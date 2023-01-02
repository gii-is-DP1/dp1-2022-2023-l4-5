
package org.springframework.samples.nt4h.game;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameTest {
    @Autowired
    public HeroService heroService;
    @Autowired
    public UserService userService;
    @Autowired
    public GameService gameService;
    public Game game;


    @BeforeEach
    void setUp() throws FullGameException, RoleAlreadyChosenException {
        User user = userService.getUserById(1);
        Hero hero = heroService.getHeroById(1);
        HeroInGame heroInGame = HeroInGame.createHeroInGame(hero, user.getPlayer());
        game = Game.createGame("Prueba", Accessibility.PUBLIC, Mode.UNI_CLASS, 4, null);
        user.createPlayer(game);
        Player player = user.getPlayer();
        player.addHero(heroInGame);
        game.addPlayer(player);
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
        Player player6 = new Player();
        this.game.addPlayer(player1);
        this.game.addPlayer(player2);
        this.game.addPlayer(player3);
        this.game.addPlayer(player4);
        Assertions.assertThrows(FullGameException.class, () -> {
            this.game .addPlayer(player5);
        });
    }
}
