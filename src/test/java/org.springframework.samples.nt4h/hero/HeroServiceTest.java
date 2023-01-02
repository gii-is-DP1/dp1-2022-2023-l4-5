package org.springframework.samples.nt4h.hero;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Accessibility;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
class HeroServiceTest {
    @Autowired
    protected HeroService heroService;
    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;
    private int idGame;

    private final int idHero = 1;
    private String nameHero;

    @BeforeEach
    void setUp() throws RoleAlreadyChosenException, FullGameException {
        User user = userService.getUserById(1);
        Hero hero = heroService.getHeroById(idHero);
        HeroInGame heroInGame = HeroInGame.createHeroInGame(hero, user.getPlayer());
        Game game = Game.createGame("Prueba", Accessibility.PUBLIC, Mode.UNI_CLASS, 4, null);
        user.createPlayer(game);
        Player player = user.getPlayer();
        player.addHero(heroInGame);
        game.addPlayer(player);
        gameService.saveGame(game);
        idGame = game.getId();
        nameHero = hero.getName();
    }

    @AfterEach
    void tearDown() {
        gameService.deleteGameById(idGame);
    }

    @Test
    public void testFindByCorrectId() {
        assertEquals(idHero, heroService.getHeroById(idHero).getId());
    }

    @Test
    public void findByIDFalse() {
        assertThrows(NotFoundException.class, () -> heroService.getHeroById(-1));
    }

    @Test
    public void testFindByName() {
        assertEquals(nameHero, heroService.getHeroByName(nameHero).getName());
    }

}

