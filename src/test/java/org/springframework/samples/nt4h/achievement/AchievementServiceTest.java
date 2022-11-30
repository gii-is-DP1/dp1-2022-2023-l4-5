package org.springframework.samples.nt4h.achievement;


import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.achievement.Achievement;
import org.springframework.samples.nt4h.achievement.AchievementRepository;
import org.springframework.samples.nt4h.achievement.AchievementService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTest {
    @Autowired
    AchievementService acs;

    @Test
    public void findByNameTrue() {
        Achievement logro = acs.getAchievementByName("Touch Some Grass");
        assertNotNull(logro);
        assertEquals(20, logro.getThreshold());
    }

    @Test
    public void findByNameFalse() {
        Achievement logro = acs.getAchievementByName("Primeros pasos");
        assertNotNull(logro);
        assertNotEquals(20, logro.getThreshold());
    }

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
    public void existsByIdTestTrue(){
        assertTrue(acs.achievementExists(1));
    }

    @Test
    public void existByIdTestFalse(){
        assertFalse(acs.achievementExists(30));
    }


    @Test
    public void shouldInsertAchivement(){
        Achievement nuevo = new Achievement();
        nuevo.setName("Goat");
        nuevo.setDescription("Thats why he is the goat THE GOAT");
        nuevo.setThreshold(4);
        acs.saveAchievement(nuevo);
        assertEquals(nuevo,acs.getAchievementByName("Goat"));
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
    public void deleteAchievementTest(){
        acs.deleteAchievementById(1);
        assertFalse(acs.achievementExists(1));
    }
}
