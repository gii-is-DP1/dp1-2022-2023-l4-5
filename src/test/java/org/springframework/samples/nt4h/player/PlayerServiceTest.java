package org.springframework.samples.nt4h.player;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerServiceTest {
    @Autowired
    protected PlayerService playerService;
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
        player.setBirthDate(Date.from(Instant.now()));
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
        player.setBirthDate(Date.from(Instant.now()));
        playerService.savePlayer(player);
        assertEquals(player,playerService.getPlayerByName("The Goat"));
    }
    @Test
    public void shouldUpdatePlayer() throws RoleAlreadyChosenException {
        List<Player> players = playerService.getAllPlayers();
        System.out.println(players);
        Player player = playerService.getPlayerById(1);
        String OldName = player.getName();
        String NewName = OldName +"X";
        player.setName(NewName);
        playerService.savePlayer(player);
        assertEquals(NewName, playerService.getPlayerById(1).getName());
    }
    @Test
    public void shouldRoleAlreadyChosen(){
        Player player = playerService.getPlayerById(1);
        HeroInGame hero1= new HeroInGame();
        hero1.setHero(heroService.getHeroByName("Neddia"));
        HeroInGame hero2= new HeroInGame();
        hero2.setHero(heroService.getHeroByName("Feldon"));
        Set<HeroInGame> heroes= Set.of(hero1,hero2);
        player.setHeroes(heroes);
        assertThrows(RoleAlreadyChosenException.class,()->playerService.savePlayer(player));
    }
    @Test
    public void deletePlayerTest(){
        playerService.deletePlayerById(1);
        assertFalse(playerService.playerExists(1));
    }


}
