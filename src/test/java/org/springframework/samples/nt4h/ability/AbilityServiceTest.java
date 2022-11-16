package org.springframework.samples.nt4h.ability;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.samples.nt4h.achievement.Achievement;
import org.springframework.samples.nt4h.achievement.AchievementService;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbilityServiceTest {
    @Autowired
    PlayerService playerService;
    @Autowired
    AbilityService abs;

    @BeforeAll
    void setUp() throws RoleAlreadyChosenException {
        Player player = new Player();
        player.setGold(0);
        player.setGlory(0);
        player.setName("Goat");
        player.setReady(false);
        player.setSequence(1);
        player.setDamageDealed(0);
        player.setDamageDealedToNightLords(0);
        player.setBirthDate(LocalDate.now());
        playerService.savePlayer(player);

        AbilityInGame habilidad = new AbilityInGame();
        habilidad.setAbility(abs.getAbilityById(1));
        habilidad.setAttack(2);
        habilidad.setPlayer(playerService.getPlayerById(1));
        habilidad.setProduct(false);
        habilidad.setTimesUsed(0);
        abs.saveAbilityInGame(habilidad);
    }



    @Test
    public void findByNameTrue() {
        Ability nuevo = abs.getAbilityByName("Compañero Lobo");
        assertNotNull(nuevo);
        assertEquals(2, nuevo.getAttack());
    }

    @Test
    public void findByNameFalse() {
        assertThrows(NotFoundException.class,() -> abs.getAbilityByName("Primeros pasos"));
    }

    @Test
    public void findByIdTrue() {
        Ability nuevo = abs.getAbilityById(1);
        assertNotNull(nuevo);
        assertEquals(2, nuevo.getAttack());
    }

    @Test
    public void findByIdFalse() {
        Ability nuevo = abs.getAbilityById(5);
        assertNotEquals(1, nuevo.getAttack());
    }

    @Test
    public void findAllTestTrue() {
        List<Ability> ls = abs.getAllAbilities();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(32, ls.size());
    }

    @Test
    public void existsByIdTestTrue(){
        assertTrue(abs.abilityExists(1));
    }

    @Test
    public void existByIdTestFalse(){
        assertFalse(abs.abilityExists(50));
    }


    @Test
    public void shouldInsertAchivement(){
        Ability nuevo = new Ability();
        nuevo.setName("Goat");
        nuevo.setRole(Role.EXPLORER);
        nuevo.setAttack(4);
        nuevo.setQuantity(1);
        nuevo.setMaxUses(1);
        abs.saveAbility(nuevo);
        assertEquals(nuevo,abs.getAbilityByName("Goat"));
    }
    @Test
    public void shouldUpdateAchievement(){
        Ability nuevo = abs.getAbilityById(1);
        String OldName = nuevo.getName();
        String NewName = OldName +"X";
        nuevo.setName(NewName);
        abs.saveAbility(nuevo);
        assertEquals(NewName, abs.getAbilityById(1).getName());
    }

    @Test
    public void deleteAchievementTest(){
        abs.deleteAbilityById(1);
        assertThrows(DataIntegrityViolationException.class,() -> abs.abilityExists(1));
    }
//In Game----------------------------------------------------------------

    @Test
    public void findByIdIGTrue() {
        AbilityInGame nuevo = abs.getAbilityInGameById(1);
        assertNotNull(nuevo);
        assertEquals(2, nuevo.getAttack());
    }

    @Test
    public void findByIdIGFalse() {
        assertThrows(NotFoundException.class,() -> abs.getAbilityInGameById(5));
    }

    @Test
    public void findAllTestIGTrue() {
        List<AbilityInGame> ls = abs.getAllAbilityInGame();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(1, ls.size());
    }

    @Test
    public void existsByIdTestIGTrue(){
        assertTrue(abs.abilityInGameExists(1));
    }

    @Test
    public void existByIdTestIGFalse(){
        assertFalse(abs.abilityInGameExists(50));
    }


    @Test
    public void shouldInsertIGAchivement(){
        AbilityInGame nuevo = new AbilityInGame();
        nuevo.setAbility(abs.getAbilityById(1));
        nuevo.setAttack(5);
        nuevo.setPlayer(playerService.getPlayerById(1));
        nuevo.setProduct(false);
        nuevo.setTimesUsed(0);
        abs.saveAbilityInGame(nuevo);
        assertEquals(5,abs.getAbilityInGameById(2).getAttack());
    }
    @Test
    public void shouldUpdateIGAchievement(){
        AbilityInGame nuevo = abs.getAbilityInGameById(1);
        nuevo.setAttack(5);
        abs.saveAbilityInGame(nuevo);
        assertEquals(5, abs.getAbilityInGameById(1).getAttack());
    }

    @Test
    public void deleteAchievementIGTest(){
        abs.deleteAbilityById(1);
        assertThrows(DataIntegrityViolationException.class,() -> abs.abilityExists(1));
    }
}
