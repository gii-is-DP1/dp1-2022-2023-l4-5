package org.springframework.samples.nt4h.card.hero;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.nt4h.capacity.CapacityRepository;
import org.springframework.samples.nt4h.card.ability.AbilityRepository;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.nt4h.card.hero.HeroRepository;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class HeroTest {

    @Autowired
    private Validator validator;
    @Autowired
    private HeroRepository heroRepository;

    private Hero hero;

    @BeforeEach
    public void setup() {
        hero = new Hero();
        hero.setName("Test Hero");
        hero.setHealth(10);
        hero.setRole(Role.EXPLORER);
        hero.setAbilities(Lists.newArrayList());
        hero.setCapacities(Lists.newArrayList());
        hero.setMaxUses(1);
    }

    @Test
    public void testHeroProperties() {
        assertThat(hero.getName()).isEqualTo("Test Hero");
        assertThat(hero.getHealth()).isEqualTo(10);
        assertThat(hero.getRole()).isEqualTo(Role.EXPLORER);
        assertThat(hero.getAbilities()).isEmpty();
        assertThat(hero.getCapacities()).isEmpty();
    }

    @Test
    public void testHeroConstraints() {
        // Test name constraints
        hero.setName("");
        assertThat(validator.validate(hero)).isNotEmpty();
        hero.setName("A");
        assertThat(validator.validate(hero)).isNotEmpty();
        hero.setName("This is a valid name");
        assertThat(validator.validate(hero)).isEmpty();

        // Test health constraints
        hero.setHealth(null);
        assertThat(validator.validate(hero)).isEmpty();
        hero.setHealth(0);
        assertThat(validator.validate(hero)).isEmpty();

        // Test role constraints
        hero.setRole(null);
        assertThat(validator.validate(hero)).isEmpty();
        hero.setRole(Role.EXPLORER);
        assertThat(validator.validate(hero)).isEmpty();
    }

    @Test
    public void testHeroLifecycle() {
        // Test saving a new hero
        hero = heroRepository.save(hero);
        assertThat(hero.getId()).isNotNull();

        // Test updating an existing hero
        hero.setName("Updated Hero");
        hero = heroRepository.save(hero);
        assertThat(hero.getName()).isEqualTo("Updated Hero");

        // Test deleting an existing hero
        heroRepository.delete(hero);
        assertThat(heroRepository.findById(hero.getId())).isEmpty();
    }

    @Test
    public void testHeroQueries() {
        // Test finding all heroes
        heroRepository.save(hero);
        assertThat(heroRepository.findAll()).isNotEmpty();

        // Test finding a hero by name
        assertThat(heroRepository.findByName("Test Hero")).isNotNull();
    }
}
