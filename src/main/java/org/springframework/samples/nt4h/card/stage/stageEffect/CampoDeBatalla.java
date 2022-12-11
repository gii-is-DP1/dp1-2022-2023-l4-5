package org.springframework.samples.nt4h.card.stage.stageEffect;

import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;

public class CampoDeBatalla {

    public void execute(Player player, EnemyInGame enemy) {
        Integer actualGold = player.getGold();
        if(enemy.getActualHealth() == 0) {
            player.setGold(actualGold + 1);
        }
    }

}
