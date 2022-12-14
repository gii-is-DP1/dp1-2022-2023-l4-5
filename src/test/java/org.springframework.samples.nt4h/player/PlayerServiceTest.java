//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.springframework.samples.nt4h.player;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.stereotype.Service;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
@TestInstance(Lifecycle.PER_CLASS)
public class PlayerServiceTest {
    @Autowired
    protected PlayerService playerService;
    @Autowired
    protected GameService gameService;
    @Autowired
    protected HeroService heroService;

    public PlayerServiceTest() {
    }

    @BeforeAll
    void setUp() throws RoleAlreadyChosenException {
        Player player = new Player();
        player.setGold(0);
        player.setGlory(0);
        player.setName("Test");
        player.setReady(false);
        player.setSequence(1);
        player.setBirthDate(LocalDate.now());
        this.playerService.savePlayer(player);
    }

    @Test
    public void findByIDTrue() {
        Player player = this.playerService.getPlayerById(1);
        Assertions.assertNotNull(player);
        Assertions.assertEquals("Test", player.getName());
    }

    @Test
    public void findByIDFalse() {
        Player player = this.playerService.getPlayerById(1);
        Assertions.assertNotNull(player);
        Assertions.assertNotEquals("MVP", player.getName());
    }

    @Test
    public void findByNameTrue() {
        Player player = this.playerService.getPlayerByName("Test");
        Assertions.assertNotNull(player);
        Assertions.assertEquals("Test", player.getName());
    }

    @Test
    public void findByNameFalse() {
        Player player = this.playerService.getPlayerByName("Test");
        Assertions.assertNotNull(player);
        Assertions.assertNotEquals("", player.getName());
    }

    @Test
    public void findAll() {
        List<Player> ls = this.playerService.getAllPlayers();
        Assertions.assertNotNull(ls);
        Assertions.assertFalse(ls.isEmpty());
        Assertions.assertEquals(2, ls.size());
    }

    @Test
    public void shouldInsertPlayer() throws RoleAlreadyChosenException {
        Player player = new Player();
        player.setGold(0);
        player.setGlory(0);
        player.setName("TheGoat");
        player.setReady(false);
        player.setSequence(1);
        player.setBirthDate(LocalDate.now());
        this.playerService.savePlayer(player);
        Assertions.assertEquals(player, this.playerService.getPlayerByName("TheGoat"));
    }

    @Test
    public void shouldUpdatePlayer() throws RoleAlreadyChosenException {
        Player player = this.playerService.getPlayerById(1);
        String OldName = player.getName();
        String NewName = OldName + "X";
        player.setName(NewName);
        this.playerService.savePlayer(player);
        Assertions.assertEquals(NewName, this.playerService.getPlayerById(1).getName());
    }

    @Test
    public void shouldRoleExceptPlayer() throws RoleAlreadyChosenException {
        Player player = this.playerService.getPlayerById(1);
        HeroInGame hero = new HeroInGame();
        hero.setHero(this.heroService.getHeroByName("Beleth-Il"));
        HeroInGame hero1 = new HeroInGame();
        hero1.setHero(this.heroService.getHeroByName("Idril"));
        player.addHero(hero);
        Assertions.assertThrows(RoleAlreadyChosenException.class, () -> {
            player.addHero(hero1);
        });
    }

    @Test
    public void shouldAddDeckFromRole() {
        Player player = this.playerService.getPlayerById(1);
        HeroInGame hero = new HeroInGame();
        hero.setHero(this.heroService.getHeroById(1));
        Role esperado = this.heroService.getHeroById(1).getRole();
        player.setHeroes(List.of(hero));
        Game game = this.gameService.getGameById(1);
        game.setPlayers(List.of(player));
        this.playerService.addDeckFromRole(player, game.getMode());
        Role[] roles = (Role[])player.getHeroes().stream().map((h) -> {
            return h.getHero().getRole();
        }).distinct().toArray((x$0) -> {
            return new Role[x$0];
        });
        List<Integer> idHabilidades = (List)player.getInDeck().stream().map((a) -> {
            return a.getAbility().getId();
        }).collect(Collectors.toList());
        Assertions.assertTrue(esperado.getAbilities().containsAll(idHabilidades));
    }

    @Test
    public void deletePlayerTest() {
        this.playerService.deletePlayerById(1);
        Assertions.assertFalse(this.playerService.playerExists(1));
    }
}
