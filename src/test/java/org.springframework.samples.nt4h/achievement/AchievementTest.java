package org.springframework.samples.nt4h.achievement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AchievementTest {

    Achievement achievement;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeEach
    void setUp() {
        achievement = Achievement.builder().description("Fat Yoshi").threshold(1).image("https://pbs.twimg.com/media/EdvDzVXXoAE3lmY.jpg")
            .threshold(20)
            .build();
    }

    @Test
    void fine() {
        assertEquals("Fat Yoshi", achievement.getDescription());
        assertEquals(20, achievement.getThreshold());
        assertEquals("https://pbs.twimg.com/media/EdvDzVXXoAE3lmY.jpg", achievement.getImage());
    }

    @Test
    public void notFineDescription() {
        // La descripción no puede ser menor de 3 carracteres.
        achievement.setDescription("Fa");
        assertTrue(!validator.validate(achievement).isEmpty(), "Description must be at least 3 characters long.");
        // La descripción no puede ser mayor de 255 carracteres.
        achievement.setDescription("a".repeat(256));
        assertTrue(!validator.validate(achievement).isEmpty(), "Description must be at most 255 characters long.");
    }

    @Test
    void notFineThreshold() {
        // El threshold no puede ser menor de 0.
        achievement.setThreshold(-1);
        assertTrue(!validator.validate(achievement).isEmpty(), "Threshold must be at least 0.");
    }

    @Test
    void notFineImage() {
        // La imagen debe ser una URL.
        achievement.setImage("noURL");
        assertTrue(!validator.validate(achievement).isEmpty(), "Image must be a URL.");
    }

}
