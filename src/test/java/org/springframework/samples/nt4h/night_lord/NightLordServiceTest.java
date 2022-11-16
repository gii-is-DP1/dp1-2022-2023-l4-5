package org.springframework.samples.nt4h.night_lord;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.card.enemy.night_lord.NightLord;
import org.springframework.samples.nt4h.card.enemy.night_lord.NightLordService;
import org.springframework.samples.nt4h.card.enemy.orc.Orc;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NightLordServiceTest {
    @Autowired
    NightLordService nls;

    @BeforeAll
    void setUp() throws Exception {
        NightLord night= new NightLord();
        night.setHealth(8);
        night.setName("Gurdrug");
        night.setMaxUses(1);
        nls.saveNightLord(night);
        NightLord night2= new NightLord();
        night2.setHealth(9);
        night2.setMaxUses(1);
        night2.setName("Shriekknifer");
        nls.saveNightLord(night2);
    }

    @Test
    public void findByNameTrue() {
        NightLord nuevo = nls.getNightLordByName("Gurdrug");
        assertNotNull(nuevo);
        assertEquals(8 ,nuevo.getHealth());
    }

    @Test
    public void findByNameFalse() {
        NightLord nuevo = nls.getNightLordByName("Shriekknifer");
        assertNotNull(nuevo);
        assertNotEquals(8, nuevo.getHealth());
    }

    @Test
    public void findByIdTrue() {
        NightLord nuevo = nls.getNightLordById(1);
        assertNotNull(nuevo);
        assertEquals(8, nuevo.getHealth());
    }

    @Test
    public void findByIdFalse() {
        NightLord nuevo = nls.getNightLordById(70);
        assertNull(nuevo);
    }

    @Test
    public void findAllTestTrue() {
        List<NightLord> ls = nls.getAllNightLords();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(2, ls.size());
    }
    @Test
    public void findAllTestFalse() {
        List<NightLord> ls = nls.getAllNightLords();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertNotEquals(30, ls.size());
    }

    @Test
    public void existsByIdTestTrue(){
        assertTrue(nls.nightLordExists(1));
    }

    @Test
    public void existByIdTestFalse(){
        assertFalse(nls.nightLordExists(50));
    }


    @Test
    public void shouldInsertOrc(){
        NightLord nuevo = new NightLord();
        nuevo.setName("GOAT");
        nuevo.setHealth(9);
        nuevo.setMaxUses(1);
        nls.saveNightLord(nuevo);
        assertEquals(nuevo, nls.getNightLordByName("GOAT"));
    }
    @Test
    public void shouldUpdateOrc(){
        NightLord nuevo = nls.getNightLordById(2);
        Integer OldHealth = nuevo.getHealth();
        Integer NewHealth = OldHealth + 1;
        nuevo.setHealth(NewHealth);
        nls.saveNightLord(nuevo);
        assertEquals(NewHealth, nls.getNightLordById(2).getHealth());
    }

    @Test
    public void deleteOrcTest(){
        nls.deleteNightLordById(1);
        assertFalse(nls.nightLordExists(1));
    }
}
