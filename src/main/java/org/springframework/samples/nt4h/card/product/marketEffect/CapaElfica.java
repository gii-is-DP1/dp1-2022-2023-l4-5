package org.springframework.samples.nt4h.card.product.marketEffect;

import org.springframework.samples.nt4h.action.DenyDamageEnemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class CapaElfica {
    public void execute(Player player, EnemyInGame enemy) {
        new DenyDamageEnemy(enemy).executeAction();
    }
}
