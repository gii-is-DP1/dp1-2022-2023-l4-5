package org.springframework.samples.nt4h.card.ability.rogue;

import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class Enganiar {
    public void execute(Player player, EnemyInGame enemy) {
        if (player.getGold() >= 2) {
            new GainGold(-2, player).execute();
            new DenyDamageEnemy(enemy).execute();
        } else {
            throw new IllegalStateException("No tienes suficiente oro para uasr esta habilidad");
        }
    }
}
