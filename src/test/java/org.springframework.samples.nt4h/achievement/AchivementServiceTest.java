package org.springframework.samples.nt4h.achievement;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchivementServiceTest {
    @Autowired
    AchievementService acs;

    @Test
    public void findByIdTrue() {
        Achievement logro = acs.getAchievementById(1);
        assertNotNull(logro);
        assertEquals(1, logro.getThreshold());
    }

    @Test
    public void findByIdFalse() {
        Achievement logro = acs.getAchievementById(5);
        assertNotNull(logro);
        assertNotEquals(1, logro.getThreshold());
    }

    @Test
    public void findAllTestTrue() {
        List<Achievement> ls = acs.getAllAchievements();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(15, ls.size());
    }

    @Test
    public void shouldInsertAchivement(){
        Achievement nuevo = new Achievement();
        nuevo.setName("Goat");
        nuevo.setDescription("Thats why he is the goat THE GOAT");
        nuevo.setThreshold(4);
        acs.saveAchievement(nuevo);
        List<Achievement> allAchievements = acs.getAllAchievements();
        assertEquals(nuevo,acs.getAchievementById(allAchievements.size()));
    }
    @Test
    public void shouldUpdateAchievement(){
        Achievement nuevo = acs.getAchievementById(1);
        String OldName = nuevo.getName();
        String NewName = OldName +"X";
        nuevo.setName(NewName);
        acs.saveAchievement(nuevo);
        assertEquals(NewName, acs.getAchievementById(1).getName());
    }
    @Test
    public void existDeleteAchievementTest() {
        acs.deleteAchievementById(2);
        assertThrows(NotFoundException.class,() -> acs.getAchievementById(2));
    }


}
