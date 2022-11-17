package org.springframework.samples.nt4h.card.ability.rogueEffects;

import org.springframework.samples.nt4h.action.ProtectAlly;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class EnLasSombras {
    public void execute(Player player, EnemyInGame enemy) {
        //new Attack(1, player, enemy).executeAction();
        new ProtectAlly(2, player).executeAction();
    }
}
