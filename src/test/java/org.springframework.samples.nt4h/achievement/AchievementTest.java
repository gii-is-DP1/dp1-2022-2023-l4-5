package org.springframework.samples.nt4h.achievement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class AchievementTest {

    @Autowired
    private AchievementService achievementService;

    private Achievement achievement;

    @Autowired
    private Validator validator;

    @BeforeEach
    public void setup() {
        achievement = new Achievement();
        achievement.setName("Test Achievement");
        achievement.setDescription("This is a test achievement");
        achievement.setThreshold(100);
        achievement.setImage("https://image.com/image.jpg");
    }

    @Test
    public void testAchievementProperties() {
        assertThat(achievement.getName()).isEqualTo("Test Achievement");
        assertThat(achievement.getDescription()).isEqualTo("This is a test achievement");
        assertThat(achievement.getThreshold()).isEqualTo(100);
        assertThat(achievement.getImage()).isEqualTo("https://image.com/image.jpg");
    }

    @Test
    public void testAchievementConstraints() {
        // Test name constraints
        achievement.setName("");
        assertThat(validator.validate(achievement)).isNotEmpty();
        achievement.setName("A");
        assertThat(validator.validate(achievement)).isNotEmpty();
        achievement.setName("This is a very long name that exceeds the maximum length of 255 characters. This is a very long name that exceeds the maximum length of 255 characters. This is a very long name that exceeds the maximum length of 255 characters. This is a very long name that exceeds the maximum length of 255 characters. This is a very long name that exceeds the maximum length of 255 characters. This is a very long name that exceeds the maximum length of 255 characters. This is a very long name that exceeds the maximum length of 255 characters.");
        assertThat(validator.validate(achievement)).isNotEmpty();

        // Test description constraints
        achievement.setName("Test Achievement");
        achievement.setDescription("");
        assertThat(validator.validate(achievement)).isNotEmpty();
        achievement.setDescription("A");
        assertThat(validator.validate(achievement)).isNotEmpty();
        achievement.setDescription("This is a very long description that exceeds the maximum length of 255 characters. This is a very long description that exceeds the maximum length of 255 characters. This is a very long description that exceeds the maximum length of 255 characters. This is a very long description that exceeds the maximum length of 255 characters. This is a very long description that exceeds the maximum length of 255 characters. This is a very long description that exceeds the maximum length of 255 characters. This is a very long description that exceeds the maximum length of 255 characters.");
        assertThat(validator.validate(achievement)).isNotEmpty();

        // Test threshold constraints
        achievement.setDescription("Test Description");
        achievement.setThreshold(-1);
        assertThat(validator.validate(achievement)).isNotEmpty();

        // Test image constraints
        achievement.setThreshold(100);
        achievement.setImage("invalid_url");
        assertThat(validator.validate(achievement)).isNotEmpty();
    }

    @Test
    public void testAchievementLifecycle() {
        // Test saving a new achievement
        achievementService.saveAchievement(achievement);
        assertThat(achievement.getId()).isNotNull();

        // Test updating an existing achievement
        achievement.setName("Updated Test Achievement");
        achievementService.saveAchievement(achievement);
        achievement = achievementService.getAchievementById(achievement.getId());
        assertThat(achievement.getName()).isEqualTo("Updated Test Achievement");

        // Test deleting an achievement
        achievementService.deleteAchievementById(achievement.getId());
        assertThatThrownBy(() -> achievementService.getAchievementById(achievement.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void testAchievementQueries() {
        // Test finding all achievements
        achievementService.saveAchievement(achievement);
        assertThat(achievementService.getAllAchievements().size()).isEqualTo(16);

        // Test finding an achievement by ID
        assertThat(achievementService.getAchievementById(achievement.getId())).isEqualTo(achievement);
    }
}
