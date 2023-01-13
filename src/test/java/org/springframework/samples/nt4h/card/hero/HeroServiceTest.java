package org.springframework.samples.nt4h.card.hero;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @MockBean
    private Advise advise;

    @BeforeEach
    void setUp() throws FullGameException, RoleAlreadyChosenException {
        User user = userService.getUserById(1);
        Game game = Game.createGame( "Prueba",   Mode.UNI_CLASS, 2, "");
        Player player = Player.createPlayer(user, game, true);
        System.out.println("numPlayers: " + game.getPlayers().size());
        game.setFinishDate(LocalDateTime.of(2020, 1, 2, 0, 0));
        Hero hero = heroService.getHeroById(idHero);
        HeroInGame heroInGame = HeroInGame.createHeroInGame(hero, user.getPlayer());
        player.addHero(heroInGame);
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

