package org.springframework.samples.nt4h.hero;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
 class HeroServiceTest {
    @Autowired
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
        assertNotEquals("Lisavete",hero.getName());
    }
    @Test
    public void findByNameTrue() {
        Hero hero = heroService.getHeroByName("Lisavette");
        assertNotNull(hero);
        assertEquals(3, hero.getHealth());
    }
    @Test
    public void findByNameFalse() {
        Hero hero = heroService.getHeroByName("Lisavette");
        assertNotNull(hero);
        assertNotEquals(2, hero.getHealth());
    }
    @Test
    void shouldFindAllHero() {
        List<Hero> list = heroService.getAllHeros();
        assertNotNull(list);
        assertEquals(8,list.size());
        assertFalse(list.isEmpty());
    }
    @Test
    @Transactional
    public void shouldInsertHero() {

        Hero hero = new Hero();
        hero.setName("Lis");
        hero.setHealth(3);
        hero.setRole(Role.EXPLORER);
        Capacity capacity= new Capacity();
        capacity.setStateCapacity(StateCapacity.EXPERTISE);
        capacity.setLessDamage(true);
        List<Capacity>capacities= List.of(capacity);
        hero.setCapacities(capacities);
        Ability ability= new Ability();
        List<Ability>abilities= List.of(ability);
        hero.setAbilities(abilities);
        this.heroService.saveHero(hero);
        assertEquals(heroService.getHeroByName("Lis"), hero);

    }
    @Test
    @Transactional
    void shouldUpdateHero() {
        Hero hero = this.heroService.getHeroById(1);
        String oldName = hero.getName();
        String newName = oldName + "X";
        hero.setName(newName);
        this.heroService.saveHero(hero);
        hero = this.heroService.getHeroById(1);
        assertEquals(hero.getName(),newName);
    }
    @Test
    @Transactional
    void existHero() {
        heroService.heroExists(1);
    }
    //HeroInGame
    @Test
    @Transactional
    public void shouldInsertHeroInGame() {

        HeroInGame hero = new HeroInGame();
        hero.setEffectUsed(2);
        hero.setActualHealth(3);
        Hero h= heroService.getHeroById(1);
        hero.setHero(h);
        Player p = new Player();
        hero.setPlayer(p);
        this.heroService.saveHeroInGame(hero);
        assertEquals(this.heroService.getHeroInGameById(1),hero);

    }
    @Test
    @Transactional
    void shouldUpdateHeroInGame() {
        HeroInGame h = new HeroInGame();
        h.setActualHealth(3);
        this.heroService.saveHeroInGame(h);
        HeroInGame hero = this.heroService.getHeroInGameById(1);
        Integer oldValue = hero.getActualHealth();
        Integer newValue = oldValue + 1;
        hero.setActualHealth(newValue);
        this.heroService.saveHeroInGame(hero);
        hero = this.heroService.getHeroInGameById(1);
        assertEquals(hero.getActualHealth(),newValue);
    }
    @Test
    @Transactional
    void existHeroInGame() {
        heroService.heroInGameExists(1);
    }

}
