package org.springframework.samples.nt4h.card.ability.rogueEffects;

import org.springframework.samples.nt4h.action.DenyDamageEnemy;
import org.springframework.samples.nt4h.action.GainGold;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class Enganiar {
    public void execute(Player player, EnemyInGame enemy) {
        if (player.getGold() >= 2) {
            new GainGold(-2, player).executeAction();
            new DenyDamageEnemy(enemy).executeAction();
        } else {
            throw new IllegalStateException("No tienes suficiente oro para uasr esta habilidad");
        }
    }
}
