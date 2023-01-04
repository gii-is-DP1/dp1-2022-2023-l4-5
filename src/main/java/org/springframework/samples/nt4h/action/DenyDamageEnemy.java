package org.springframework.samples.nt4h.action;


import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;

@AllArgsConstructor
public class DenyDamageEnemy implements Action {


    private EnemyInGame targetedEnemy;

    @Override
    public void executeAction() {
        targetedEnemy.setNoAttackThisTurn(true);
    }
}
