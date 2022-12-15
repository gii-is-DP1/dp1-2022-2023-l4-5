package org.springframework.samples.nt4h.hero;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.capacity.StateCapacity;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
@TestInstance(Lifecycle.PER_CLASS)
class HeroServiceTest {
    @Autowired
    protected HeroService heroService;

    HeroServiceTest() {
    }

    @BeforeEach
    void setUp() throws Exception {
        Hero hero = new Hero();
        hero.setName("Test");
        hero.setMaxUses(0);
        hero.setRole(Role.EXPLORER);
        hero.setHealth(3);
        hero.setAbilities(List.of());
        hero.setCapacities(List.of());
        HeroInGame heroInGame = new HeroInGame();
        heroInGame.setPlayer(new Player());
        heroInGame.setEffectUsed(0);
        heroInGame.setActualHealth(1);
        this.heroService.saveHeroInGame(heroInGame);
    }

    @Test
    public void findByIDTrue() {
        Hero hero = this.heroService.getHeroById(2);
        Assertions.assertNotNull(hero);
        Assertions.assertEquals("Lisavette", hero.getName());
    }

    @Test
    public void findByIDFalse() {
        Hero hero = this.heroService.getHeroById(1);
        Assertions.assertNotNull(hero);
        Assertions.assertNotEquals("Lisavete", hero.getName());
    }

    @Test
    public void findByNameTrue() {
        Hero hero = this.heroService.getHeroByName("Lisavette");
        Assertions.assertNotNull(hero);
        Assertions.assertEquals(3, hero.getHealth());
    }

    @Test
    public void findByNameFalse() {
        Hero hero = this.heroService.getHeroByName("Lisavette");
        Assertions.assertNotNull(hero);
        Assertions.assertNotEquals(2, hero.getHealth());
    }

    @Test
    @Transactional
    void existHero() {
        this.heroService.heroExists(1);
    }

}

