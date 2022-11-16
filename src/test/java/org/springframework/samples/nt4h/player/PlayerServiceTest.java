package org.springframework.samples.nt4h.player;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTest {
    @Autowired
    protected PlayerService playerService;
    /*

    void ini() {
        //OMG quiero poner esto en model de Player
        Player player = new Player();
        player.setGold(0);
        player.setGlory(0);
        player.setName("Goat");
        player.setReady(false);
        player.setSequence(1);
        player.setDamageDealed(0);
        player.setDamageDealedToNightLords(0);
        player.setEvasion(true);
        player.setBirthDate(Date.from(Instant.now()));
        playerService.savePlayer(player);
    }

    @Test
    public void findByIDTrue(){
        ini();
        Player player = playerService.getPlayerById(1);
        assertNotNull(player);
        assertEquals("Goat", player.getName());
    }
    @Test
    public void findByIDFalse(){
        ini();
        Player player = playerService.getPlayerById(1);
        assertNotNull(player);
        assertNotEquals("MVP", player.getName());
    }
    @Test
    public void findByNameTrue(){
        ini();
        Player player = playerService.getPlayerByName("Goat");
        assertNotNull(player);
        assertEquals(1, player.getId());
    }
    @Test
    public void findByNameFalse(){
        ini();
        Player player = playerService.getPlayerByName("Goat");
        assertNotNull(player);
        assertNotEquals(2, player.getId());
    }
    @Test
    public void findAll(){
        ini();
        List<Player> ls= playerService.getAllPlayers();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(1,ls.size());
    }
    @Test
    public void shouldInsertPlayer(){
        Player player = new Player();
        player.setGold(0);
        player.setGlory(0);
        player.setName("Goat");
        player.setReady(false);
        player.setSequence(1);
        player.setDamageDealed(0);
        player.setDamageDealedToNightLords(0);
        player.setEvasion(true);
        player.setBirthDate(Date.from(Instant.now()));
        playerService.savePlayer(player);
        assertEquals(player,playerService.getPlayerByName("Goat"));
    }
    @Test
    public void shouldUpdatePlayer(){
        ini();
        Player player = playerService.getPlayerById(1);
        String OldName = player.getName();
        String NewName = OldName +"X";
        player.setName(NewName);
        playerService.savePlayer(player);
        assertEquals(NewName, playerService.getPlayerById(1).getName());
    }


     */
}
