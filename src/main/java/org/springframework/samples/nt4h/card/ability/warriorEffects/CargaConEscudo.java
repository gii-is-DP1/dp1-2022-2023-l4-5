package org.springframework.samples.nt4h.card.ability.warriorEffects;

import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class CargaConEscudo {
    public void execute(Player player, EnemyInGame enemy) {
        new Attack(2, player, enemy).executeAction();
        //new PreventDamage(2, player).execute();
    }
}
