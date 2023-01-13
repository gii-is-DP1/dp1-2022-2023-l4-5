package org.springframework.samples.nt4h.achievement;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class AchievementServiceTest {
    @Autowired
    AchievementService achievementService;
    private String nameAchievement;
    private final int idAchievement = 1;
    private int numsAchievements;
    private Achievement achievement;

    @MockBean
    private Advise advise;

    @BeforeEach
    void setUp() {
        List<Achievement> achievements = achievementService.getAllAchievements();
        numsAchievements = achievements.size();
        nameAchievement = achievements.get(idAchievement-1).getName();
        achievement = Achievement.builder().description("Thats why he is the goat THE GOAT").threshold(100).build();
        achievement.setName("Goat");
    }

    @Test
    void testFindById() {
        assertEquals(nameAchievement, achievementService.getAchievementById(idAchievement).getName());
    }

    @Test
    void testFindByIncorrectId() {
        assertThrows(NotFoundException.class, () -> achievementService.getAchievementById(-1));
    }

    @Test
    void testFindAll() {
        assertEquals(numsAchievements, achievementService.getAllAchievements().size());
    }

    @Test
    void testSave(){

        achievementService.saveAchievement(achievement);
        assertEquals(numsAchievements+1, achievementService.getAllAchievements().size());
    }
    @Test
    void testUpdate(){
        Achievement oldAchievement = achievementService.getAchievementById(idAchievement+1);
        oldAchievement.setName(nameAchievement);
        achievementService.saveAchievement(oldAchievement);
        assertEquals(nameAchievement, achievementService.getAchievementById(idAchievement).getName());
    }
    @Test
    void testDelete() {
        achievementService.deleteAchievementById(idAchievement);
        assertEquals(numsAchievements-1, achievementService.getAllAchievements().size());
    }
}
