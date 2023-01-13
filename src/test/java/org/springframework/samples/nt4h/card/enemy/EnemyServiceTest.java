package org.springframework.samples.nt4h.card.enemy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class EnemyServiceTest {
    @Autowired
    EnemyService enemyService;
    private Enemy enemy;
    private EnemyInGame enemyInGame;
    private int idEnemyInGame;
    private int numsEnemiesInGame;

    @MockBean
    private Advise advise;
    @Test
    void testFindById() {
        assertEquals(enemyInGame, enemyService.getEnemyInGameById(idEnemyInGame));
    }



    @Test
    void testUpdate(){
        EnemyInGame oldEnemyInGame = enemyService.getEnemyInGameById(idEnemyInGame);
        Integer newHealth = (enemyInGame.getActualHealth()+1)%enemy.getHealth();
        EnemyInGame newEnemyInGame = oldEnemyInGame.toBuilder().actualHealth(newHealth).build();
        newEnemyInGame.setId(idEnemyInGame);
        enemyService.saveEnemyInGame(newEnemyInGame);
        assertEquals(newHealth, enemyService.getEnemyInGameById(idEnemyInGame).getActualHealth());
    }
    @Test
    public void testGetNightLord(){
        assertTrue(enemyService.getNightLord().getIsNightLord());
    }
    @Test
    void testAddOrcsToGame(){
        List<EnemyInGame> orcs= enemyService.addOrcsToGame(4);
        assertEquals(27,orcs.size());
        assertTrue(orcs.stream().noneMatch(x-> x.getEnemy().getIsNightLord()));
    }
    @Test
    void testAddWarlordToGame(){
        assertTrue(enemyService.addNightLordToGame().isNightLord());
    }
}
