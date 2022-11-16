package org.springframework.samples.nt4h.player;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerServiceTest {
    @Autowired
    protected PlayerService playerService;
    @Autowired
    protected GameService gameService;

    @Autowired
    protected HeroService heroService;
    @BeforeAll
     void setUp() throws RoleAlreadyChosenException {
        //OMG quiero poner esto en model de Player
        Player player = new Player();
        player.setGold(0);
        player.setGlory(0);
        player.setName("Goat");
        player.setReady(false);
        player.setSequence(1);
        player.setDamageDealed(0);
        player.setDamageDealedToNightLords(0);
        player.setBirthDate(LocalDate.now());
        playerService.savePlayer(player);

    }

    @Test
    public void findByIDTrue(){
        Player player = this.playerService.getPlayerById(1);
        assertNotNull(player);
        assertEquals("Goat", player.getName());
    }
    @Test
    public void findByIDFalse(){
        Player player = this.playerService.getPlayerById(1);
        assertNotNull(player);
        assertNotEquals("MVP", player.getName());
    }
    @Test
    public void findByNameTrue(){
        Player player = playerService.getPlayerByName("Goat");
        assertNotNull(player);
        assertEquals("Goat", player.getName());
    }
    @Test
    public void findByNameFalse(){
        Player player = playerService.getPlayerByName("Goat");
        assertNotNull(player);
        assertNotEquals("", player.getName());
    }
    @Test
    public void findAll(){
        List<Player> ls= playerService.getAllPlayers();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(1,ls.size());
    }
    @Test
    public void shouldInsertPlayer() throws RoleAlreadyChosenException {
        Player player = new Player();
        player.setGold(0);
        player.setGlory(0);
        player.setName("The Goat");
        player.setReady(false);
        player.setSequence(1);
        player.setDamageDealed(0);
        player.setDamageDealedToNightLords(0);
        player.setBirthDate(LocalDate.now());
        playerService.savePlayer(player);
        assertEquals(player,playerService.getPlayerByName("The Goat"));
    }
    @Test
    public void shouldUpdatePlayer() throws RoleAlreadyChosenException {
        Player player = playerService.getPlayerById(1);
        String OldName = player.getName();
        String NewName = OldName +"X";
        player.setName(NewName);
        playerService.savePlayer(player);
        assertEquals(NewName, playerService.getPlayerById(1).getName());
    }
    @Test
    public void shouldRoleExceptPlayer() throws RoleAlreadyChosenException {
        Player player = playerService.getPlayerById(1);
        HeroInGame hero = new HeroInGame();
        hero.setHero(heroService.getHeroByName("Beleth-Il"));
        HeroInGame hero1 = new HeroInGame();
        hero1.setHero(heroService.getHeroByName("Idril"));
        player.addHero(hero);
        player.addHero(hero1);
        assertThrows(RoleAlreadyChosenException.class,()->playerService.savePlayer(player, Mode.MULTI_CLASS));
    }
    @Test
    public void shouldAddDeckFromRole(){
        Player player = playerService.getPlayerById(1);
        HeroInGame hero = new HeroInGame();
        hero.setHero(heroService.getHeroById(1));
        Role esperado= heroService.getHeroById(1).getRole();
        player.setHeroes(List.of(hero));
        Game game= gameService.getGameById(1);
        game.setPlayers(List.of(player));
        playerService.addDeckFromRole(player,game.getMode());
        Role[] roles = player.getHeroes().stream().map(h -> h.getHero().getRole()).distinct().toArray(Role[]::new);
        List<Integer> idHabilidades= player.getInDeck().stream().map(a->a.getAbility().getId()).collect(Collectors.toList());
        assertTrue(esperado.getAbilities().containsAll(idHabilidades));

    }
    @Test
    public void deletePlayerTest(){
        playerService.deletePlayerById(1);
        assertFalse(playerService.playerExists(1));
    }




}
