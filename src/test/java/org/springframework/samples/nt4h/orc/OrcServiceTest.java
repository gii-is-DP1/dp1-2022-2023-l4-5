package org.springframework.samples.nt4h.orc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.card.enemy.orc.Orc;
import org.springframework.samples.nt4h.card.enemy.orc.OrcService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrcServiceTest {
    @Autowired
    OrcService ors;

    @BeforeAll
    void setUp() throws Exception {
        Orc orc= new Orc();
        orc.setHealth(2);
        orc.setMaxUses(1);
        orc.setName("Honda");
        orc.setGlory(2);
        orc.setGold(1);
        orc.setHasCure(false);
        orc.setLessDamageWizard(false);
        ors.saveOrc(orc);
        Orc orc1= new Orc();
        orc1.setHealth(2);
        orc1.setMaxUses(1);
        orc1.setName("Honda");
        orc1.setGlory(2);
        orc1.setGold(1);
        orc1.setHasCure(false);
        orc1.setLessDamageWizard(false);
        ors.saveOrc(orc1);
    }


    @Test
    public void findByNameTrue() {
        List<Orc> nuevo = ors.getOrcByName("Honda");
        assertNotNull(nuevo);
        assertFalse(nuevo.isEmpty());
        assertEquals(2 ,nuevo.get(1).getHealth());
    }

    @Test
    public void findByNameFalse() {
        List<Orc> nuevo = ors.getOrcByName("Honda");
        assertNotNull(nuevo);
        assertFalse(nuevo.isEmpty());
    }

    @Test
    public void findByIdTrue() {
        Orc nuevo = ors.getOrcById(1);
        assertNotNull(nuevo);
        assertEquals(2, nuevo.getHealth());
    }

    @Test
    public void findByIdFalse() {
        Orc nuevo = ors.getOrcById(70);
        assertNull(nuevo);
    }


    @Test
    public void findAllTestTrue() {
        List<Orc> ls = ors.getAllOrcs();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(2, ls.size());
    }
    @Test
    public void findAllTestFalse() {
        List<Orc> ls = ors.getAllOrcs();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertNotEquals(5, ls.size());
    }

    @Test
    public void existsByIdTestTrue(){
        assertTrue(ors.orcExists(1));
    }

    @Test
    public void existByIdTestFalse(){
        assertFalse(ors.orcExists(50));
    }


    @Test
    public void shouldInsertOrc(){
        Orc nuevo = new Orc();
        nuevo.setName("GOAT");
        nuevo.setMaxUses(-1);
        nuevo.setGold(0);
        nuevo.setGlory(1);
        nuevo.setHealth(4);
        nuevo.setLessDamageWizard(false);
        nuevo.setHasCure(false);
        ors.saveOrc(nuevo);
        assertEquals(nuevo,ors.getOrcByName("GOAT").get(0));
    }
    @Test
    public void shouldUpdateOrc(){
        Orc nuevo = ors.getOrcById(2);
        String OldName = nuevo.getName();
        String NewName = OldName +"X";
        nuevo.setName(NewName);
        ors.saveOrc(nuevo);
        assertEquals(NewName, ors.getOrcById(2).getName());
    }

    @Test
    public void deleteOrcTest(){
        ors.deleteOrcById(1);
        assertFalse(ors.orcExists(1));
    }
}
