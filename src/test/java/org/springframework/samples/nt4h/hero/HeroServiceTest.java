package org.springframework.samples.nt4h.hero;


import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AllArgsConstructor
 class HeroServiceTest {

    protected HeroService heroService;

    @Test
    public void findByIDTrue(){
        Hero hero = heroService.getHeroById(2);
        assertNotNull(hero);
        assertEquals("Lisavette",hero.getName() );
    }
    @Test
    public void findByIDFalse() {
        Hero hero= heroService.getHeroById(1);
        assertNotNull(hero);
        assertEquals("Lisavete",hero.getName() );
    }
    @Test
    public void findByNameTrue() {
        Optional<Hero> hero = heroService.getHeroByName("Lisavete");
        assertNotNull(hero);
        assertEquals(3,hero.get().getHealth());
    }
    @Test
    public void findByNameFalse() {
        Optional<Hero> hero = heroService.getHeroByName("Lisavete");
        assertNotNull(hero);
        assertEquals(2,hero.get().getHealth());
    }
}
