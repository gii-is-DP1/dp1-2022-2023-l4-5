package org.springframework.samples.nt4h.enemy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnemyServiceTest {
    @Autowired
    EnemyService ens;

    @BeforeAll
    void setUp() throws RoleAlreadyChosenException {
        EnemyInGame enemy = new EnemyInGame();
        enemy.setId(1);
        enemy.setActualHealth(4);
        ens.saveEnemyInGame(enemy);
    }
    @Test
    public void findByIdIGTrue() {
        EnemyInGame nuevo = ens.getEnemyInGameById(1);
        assertNotNull(nuevo);
        assertEquals(4, nuevo.getActualHealth());
    }

    @Test
    public void findByIdIGFalse() {
        assertThrows(NotFoundException.class,() -> ens.getEnemyInGameById(5));
    }

    @Test
    public void findAllTestIGTrue() {
        List<EnemyInGame> ls = ens.getAllEnemyInGame();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(1, ls.size());
    }

    @Test
    public void existsByIdTestIGTrue(){
        assertTrue(ens.enemyInGameExists(1));
    }

    @Test
    public void existByIdTestIGFalse(){
        assertFalse(ens.enemyInGameExists(50));
    }


    @Test
    public void shouldUpdateIGEnemy(){
        EnemyInGame nuevo = ens.getEnemyInGameById(1);
        nuevo.setActualHealth(2);
        ens.saveEnemyInGame(nuevo);
        assertEquals(2, ens.getEnemyInGameById(1).getActualHealth());
    }
    @Test
    public void getNightLordTest(){
        Enemy warlord= ens.getNightLord();
        assertEquals(warlord.getIsNightLord(), true);
    }
    @Test
    public void addOrcsToGameTest(){
        List<EnemyInGame> orcs= ens.addOrcsToGame();
        assertEquals(27,orcs.size());
        assertTrue(orcs.stream().allMatch(x->x.getEnemy().getIsNightLord()==false));
    }
    @Test
    public void addWarlordToGameTest(){
        EnemyInGame warlors= ens.addNightLordToGame();
        assertTrue(warlors.isNightLord());
    }

    @AfterAll
    @Test
    public void deleteEnemyIGTest(){
        ens.deleteEnemyInGameById(1);
        assertFalse(ens.enemyInGameExists(1));
    }
}
