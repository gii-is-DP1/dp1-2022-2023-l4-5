
package org.springframework.samples.nt4h.game;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTest {
    @Autowired
    protected GameService gameService;
    @Autowired
    protected UserService userService;
    @Autowired
    private HeroService heroService;
    private int idGame;
    private Game game;
    private int numGames;


    @BeforeEach
    void setUp() throws FullGameException, RoleAlreadyChosenException {
        User user = userService.getUserById(1);
        game = Game.createGame( "Prueba",   Mode.UNI_CLASS, 2, "");
        Player player = Player.createPlayer(user, game, true);
        game.setFinishDate(LocalDateTime.of(2020, 1, 2, 0, 0));
        game.setHasStages(true);
        Hero hero = heroService.getHeroById(1);
        HeroInGame heroInGame = HeroInGame.createHeroInGame(hero, user.getPlayer());
        player.addHero(heroInGame);
        gameService.saveGame(game);
        idGame = game.getId();
        numGames = gameService.getAllGames().size();
        /*
        User user = userService.getUserById(1);
        Hero hero = heroService.getHeroById(1);
        HeroInGame heroInGame = HeroInGame.createHeroInGame(hero, user.getPlayer());
        game = Game.createGame("Prueba", Accessibility.PUBLIC, Mode.UNI_CLASS, 4, null);
        user.createPlayer(game);
        Player player = user.getPlayer();
        player.addHero(heroInGame);
        game.addPlayer(player);
        gameService.saveGame(game);
        idGame = game.getId();
        numGames = gameService.getAllGames().size();
         */
    }

    @AfterEach
    void tearDown() {
        gameService.deleteGame(gameService.getGameById(idGame));
    }

    @Test
    public void testFindById() {
        assertEquals(game, gameService.getGameById(idGame));
    }
    @Test
    public void testFindByIncorrectId() {
        assertThrows(NotFoundException.class, () -> gameService.getGameById(-1));
    }
    @Test
    public void testFindAll() {
        assertEquals(numGames, gameService.getAllGames().size());
    }

    /*@Test
    public void testUpdate() {
        Game oldGame = this.gameService.getGameById(idGame);
        Game newGame = oldGame.toBuilder().phase(Phase.ENEMY_ATTACK).build();
        newGame.setId(idGame);
        newGame.setName(oldGame.getName());
        this.gameService.saveGame(newGame);
        assertEquals(newGame.getPhase(), this.gameService.getGameById(idGame).getPhase());
    }*/
}
