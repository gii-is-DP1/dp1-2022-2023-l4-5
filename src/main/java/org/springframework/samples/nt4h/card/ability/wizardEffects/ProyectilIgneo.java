package org.springframework.samples.nt4h.card.ability.wizardEffects;

import org.springframework.samples.nt4h.action.GainGlory;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class ProyectilIgneo {
    public void execute(Player player, EnemyInGame enemy) {
        //new Attack(2, player, enemy).executeAction();
        new GainGlory(1, player).executeAction();
    }
}
