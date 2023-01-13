//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.springframework.samples.nt4h.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
public class PlayerServiceTest {

    @Autowired
    protected PlayerService playerService;
    @Autowired
    protected GameService gameService;
    @Autowired
    protected HeroService heroService;
    @Autowired
    protected UserService userService;
    private int idPlayer;
    private int idGame;
    private String namePlayer;
    private Player player;

    @MockBean
    private Advise advise;

    public PlayerServiceTest() {
    }

    @BeforeEach
    void setUp() throws RoleAlreadyChosenException, FullGameException, HeroAlreadyChosenException {
        User user = userService.getUserById(1);
        Hero hero = heroService.getHeroById(1);
        HeroInGame heroInGame = HeroInGame.createHeroInGame(hero, user.getPlayer());
        Game game = Game.createGame( "Test Game",   Mode.MULTI_CLASS, 2, "test123");
        player = Player.createPlayer(user, game, true);
        game.setStartDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        game.setFinishDate(LocalDateTime.of(2020, 1, 2, 0, 0));
        gameService.saveGame(game);
        game.addPlayerWithNewHero(player, heroInGame);
        idPlayer = player.getId();
        idGame = game.getId();
        namePlayer = player.getName();

    }

    @Test
    public void testFindByCorrectId() {
        assertEquals(idPlayer, playerService.getPlayerById(idPlayer).getId());
    }

    @Test
    public void testFindByIncorrectId() {
        assertThrows(NotFoundException.class, () -> playerService.getPlayerById(-1));
    }

    @Test
    public void testUpdate() {
        Player newPlayer = this.playerService.getPlayerById(idPlayer);
        newPlayer.setName("Nuevo nombre");
        playerService.savePlayer(newPlayer);
        assertEquals(newPlayer.getName(), this.playerService.getPlayerById(idPlayer).getName());
    }

    @Test
    public void testSaveWithRoleAlreadyChosenException() throws RoleAlreadyChosenException {
        HeroInGame heroInGame1 = HeroInGame.createHeroInGame(this.heroService.getHeroByName("Beleth-Il"), player);
        HeroInGame heroInGame2 = HeroInGame.createHeroInGame(this.heroService.getHeroByName("Idril"), player);
        player.addHero(heroInGame1);
        assertThrows(RoleAlreadyChosenException.class, () -> player.addHero(heroInGame2));
    }

    @Test
    public void deletePlayerTest() {
        playerService.deletePlayerById(idPlayer);
        System.out.println("Game: " + gameService.getGameById(idGame).getPlayers());
    }
}
