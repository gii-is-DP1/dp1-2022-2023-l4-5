package org.springframework.samples.nt4h.hero;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.capacity.StateCapacity;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.hero.*;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class HeroServiceTest {
    @Autowired
    protected HeroService heroService;

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
        heroService.saveHeroInGame(heroInGame);
    }

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
        ability.setAttack(1);
        ability.setQuantity(1);
        ability.setMaxUses(1);
        ability.setRole(Role.EXPLORER);
        List<Ability>abilities= List.of(ability);
        hero.setAbilities(abilities);
        hero.setMaxUses(1);
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
    /*
    @Test
    @Transactional
    public void shouldInsertHeroInGame() {

        HeroInGame hero = new HeroInGame();
        hero.setEffectUsed(2);
        hero.setActualHealth(3);
        Hero h= heroService.getHeroById(1);
        hero.setHero(h);
        Player p = new Player();
        Game game = new Game();
        game.setMode(Mode.UNI_CLASS);
        game.setStartDate(LocalDateTime.now());
        game.setPlayers(List.of(p));
        p.setGame(game);
        p.setBirthDate(LocalDate.now());
        hero.setPlayer(p);
        heroService.saveHeroInGame(hero);
        assertEquals(heroService.getHeroInGameById(1),hero);

    }

     */
    /*
    @Test
    @Transactional
    void shouldUpdateHeroInGame() {
        HeroInGame hero = heroService.getHeroInGameById(1);
        Integer oldValue = hero.getActualHealth();
        Integer newValue = oldValue + 1;
        hero.setActualHealth(newValue);
        heroService.saveHeroInGame(hero);
        hero = heroService.getHeroInGameById(1);
        assertEquals(hero.getActualHealth(),newValue);
    }

     */

    @Test
    public void deleteHeroTest(){
        heroService.deleteHeroById(1);
        assertThrows(DataIntegrityViolationException.class,()->heroService.heroExists(1));
    }
    /*
    @Test
    public void deleteHeroInGameTest(){
        heroService.deleteHeroInGameById(1);
        assertFalse(heroService.heroInGameExists(1));
    }

     */


}
