package org.springframework.samples.nt4h.action.actions;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;

@AllArgsConstructor
public class GoldOnKill implements Action {

    private Integer gold;
    private EnemyInGame targetedEnemy;
    private Player activePlayer;

    @Override
    public void executeAction() {
        if (targetedEnemy.getActualHealth() <= 0) {
            Integer currentGold = activePlayer.getGold();
            activePlayer.setGold(currentGold + gold);
        }
    }
}