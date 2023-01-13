package org.springframework.samples.nt4h.achievement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
class AchievementRepositoryTest {
    @Autowired
    private AchievementRepository achievementRepository;

    private String nameAchievement;
    private final int idAchievement = 1;
    private int numsAchievements;

    @BeforeEach
    void setUp() {
        List<Achievement> achievements = achievementRepository.findAll();
        numsAchievements = achievements.size();
        nameAchievement = achievements.get(idAchievement-1).getName();
    }

    @Test
    void testFindByCorrectName() {
        assertEquals(nameAchievement, achievementRepository.findByName(nameAchievement).get().getName());
    }

    @Test
    void testFindById() {
        assertEquals(nameAchievement, achievementRepository.findById(idAchievement).get().getName());
    }

    @Test
    void testFindByIncorrectId() {
        Optional<Achievement> achievement = achievementRepository.findById(-1);
        assertFalse(achievement.isPresent());
    }

    @Test
    void testFindAll() {
        assertEquals(numsAchievements, achievementRepository.findAll().size());
    }

    @Test
    void testDelete() {
        Achievement achievement = achievementRepository.findById(idAchievement).get();
        achievementRepository.delete(achievement);
        assertEquals(numsAchievements-1, achievementRepository.findAll().size());
    }

    @Test
    void testDeleteByCorrectId() {
        achievementRepository.deleteById(idAchievement);
        assertEquals(numsAchievements-1, achievementRepository.findAll().size());
    }

    @Test
    void testDeleteByIncorrectId() {
        assertThrows(EmptyResultDataAccessException.class, () -> achievementRepository.deleteById(-1));
    }

    @Test
    void testDeleteAll() {
        achievementRepository.deleteAll();
        assertEquals(0, achievementRepository.findAll().size());
    }

    @Test
    void testUpdate() {
        Achievement achievement = achievementRepository.findById(idAchievement+1).get();
        achievement.setName(nameAchievement);
        achievementRepository.save(achievement);
        assertEquals(nameAchievement, achievementRepository.findById(idAchievement+1).get().getName());
    }
}

