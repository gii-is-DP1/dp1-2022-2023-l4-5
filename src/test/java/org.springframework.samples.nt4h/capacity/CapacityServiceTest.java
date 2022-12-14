package org.springframework.samples.nt4h.capacity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.nt4h.achievement.Achievement;
import org.springframework.samples.nt4h.achievement.AchievementService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CapacityServiceTest {

    @Autowired
    CapacityService cas;
    @Test
    public void findByStateTrue() {
        List<Capacity> nuevo = cas.getCapacityByStateCapacity(StateCapacity.MELEE);
        assertNotNull(nuevo);
        assertFalse(nuevo.isEmpty());
    }

    @Test
    public void findByStateFalse() {
        List<Capacity> nuevo = cas.getCapacityByStateCapacity(StateCapacity.PRUEBA);
        assertNotNull(nuevo);
        assertTrue(nuevo.isEmpty());
    }

    @Test
    public void findByIdTrue() {
        Capacity nuevo = cas.getCapacityById(1);
        assertNotNull(nuevo);
        assertFalse(nuevo.getLessDamage());
    }

    @Test
    public void findByIdFalse() {
        Capacity nuevo = cas.getCapacityById(5);
        assertNotNull(nuevo);
        assertFalse(nuevo.getLessDamage() == false);
    }

    @Test
    public void findAllTestTrue() {
        List<Capacity> ls = cas.getAllCapacities();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(8, ls.size());
    }

    @Test
    public void existsByIdTestTrue(){
        assertTrue(cas.capacityExists(1));
    }

    @Test
    public void existByIdTestFalse(){
        assertFalse(cas.capacityExists(30));
    }



    @Test
    public void shouldInsertCapacity(){
        Capacity nuevo = new Capacity();
        nuevo.setStateCapacity(StateCapacity.EXPERTISE);
        nuevo.setLessDamage(false);
        cas.saveCapacity(nuevo);
        List<Capacity> probar = cas.getCapacityByStateCapacity(StateCapacity.EXPERTISE);
        Capacity comparador = probar.get(2);
        assertEquals(nuevo, comparador);
    }
    @Test
    public void shouldUpdateCapacity(){
        Capacity nuevo = cas.getCapacityById(1);
        Boolean OldLD = nuevo.getLessDamage();
        Boolean NewLD = true;
        nuevo.setLessDamage(NewLD);
        cas.saveCapacity(nuevo);
        assertTrue(cas.getCapacityById(1).getLessDamage());
    }


    @Test
    public void deleteAchievementTest(){
        cas.deleteCapacityById(1);
        assertThrows(DataIntegrityViolationException.class,() -> cas.capacityExists(1));


    }
}
