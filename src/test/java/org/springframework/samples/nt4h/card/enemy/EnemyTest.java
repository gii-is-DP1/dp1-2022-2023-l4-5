package org.springframework.samples.nt4h.card.enemy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.EnemyRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class EnemyTest {
    @Autowired
    private Validator validator;

    private Enemy enemy;
    @Autowired
    private EnemyRepository enemyRepository;

    @BeforeEach
    public void setup() {
        enemy = new Enemy();
        enemy.setHealth(5);
        enemy.setHiddenGlory(2);
        enemy.setHiddenGold(1);
        enemy.setHasCure(true);
        enemy.setLessDamageWizard(true);
        enemy.setIsNightLord(false);
        enemy.setMaxUses(1);
    }

    @Test
    public void testEnemyProperties() {
        assertThat(enemy.getHealth()).isEqualTo(5);
        assertThat(enemy.getGlory()).isEqualTo(2);
        assertThat(enemy.getGold()).isEqualTo(1);
        assertThat(enemy.getHasCure()).isTrue();
        assertThat(enemy.getLessDamageWizard()).isTrue();
        assertThat(enemy.getIsNightLord()).isFalse();
    }

    @Test
    public void testEnemyConstraints() {
        // Test health constraints
        enemy.setHealth(1);
        assertThat(validator.validate(enemy)).isNotEmpty();
        enemy.setHealth(11);
        assertThat(validator.validate(enemy)).isNotEmpty();
        enemy.setHealth(null);
        assertThat(validator.validate(enemy)).isNotEmpty();
        enemy.setHealth(5);
        assertThat(validator.validate(enemy)).isEmpty();

        // Test glory constraints
        enemy.setHiddenGlory(-1);
        assertThat(validator.validate(enemy)).isNotEmpty();
        enemy.setHiddenGlory(5);
        assertThat(validator.validate(enemy)).isNotEmpty();
        enemy.setHiddenGlory(null);
        assertThat(validator.validate(enemy)).isEmpty();
        enemy.setHiddenGlory(2);
        assertThat(validator.validate(enemy)).isEmpty();

        // Test gold constraints
        enemy.setHiddenGold(-1);
        assertThat(validator.validate(enemy)).isNotEmpty();
        enemy.setHiddenGold(3);
        assertThat(validator.validate(enemy)).isNotEmpty();
        enemy.setHiddenGold(null);
        assertThat(validator.validate(enemy)).isEmpty();
        enemy.setHiddenGold(1);
        assertThat(validator.validate(enemy)).isEmpty();

        // Test hasCure constraint
        enemy.setHasCure(null);
        assertThat(validator.validate(enemy)).isNotEmpty();
        enemy.setHasCure(true);
        assertThat(validator.validate(enemy)).isEmpty();

        // Test lessDamageWizard constraint
        enemy.setLessDamageWizard(null);
        assertThat(validator.validate(enemy)).isNotEmpty();
        enemy.setLessDamageWizard(true);
        assertThat(validator.validate(enemy)).isEmpty();

        // Test isNightLord constraint
        enemy.setIsNightLord(null);
        assertThat(validator.validate(enemy)).isNotEmpty();
        enemy.setIsNightLord(true);
        assertThat(validator.validate(enemy)).isEmpty();
    }

    @Test
    public void testEnemyLifecycle() {
        // Test saving a new enemy
        enemyRepository.save(enemy);
        assertThat(enemy.getId()).isNotNull();

        // Test updating an existing enemy
        enemy.setHealth(10);
        enemyRepository.save(enemy);
        assertThat(enemyRepository.findById(enemy.getId()).get().getHealth()).isEqualTo(10);

        // Test deleting an existing enemy
        enemyRepository.delete(enemy);
        assertThat(enemyRepository.findById(enemy.getId())).isEmpty();
    }

    @Test
    public void testCapacityQueries() {
        // Test finding all enemies
        assertThat(enemyRepository.findAll()).isNotEmpty();

        // Test finding an enemy by id
        enemyRepository.save(enemy);
        assertThat(enemyRepository.findById(enemy.getId())).isNotEmpty();
    }
}
