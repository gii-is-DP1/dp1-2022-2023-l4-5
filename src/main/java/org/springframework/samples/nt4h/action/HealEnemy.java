package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;

@AllArgsConstructor
public class HealEnemy implements Action {

    private EnemyInGame enemy;

    @Override
    public void executeAction() {
        Integer enemyLife = enemy.getEnemy().getHealth();
        if(enemy.getActualHealth() > 0 && enemy.getActualHealth() < enemyLife) {
            enemy.setActualHealth(enemyLife);
        }
    }
}
