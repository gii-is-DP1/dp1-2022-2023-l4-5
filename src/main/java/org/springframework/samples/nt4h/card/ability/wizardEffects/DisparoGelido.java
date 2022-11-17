package org.springframework.samples.nt4h.card.ability.wizardEffects;

import org.springframework.samples.nt4h.action.DenyDamageEnemy;
import org.springframework.samples.nt4h.action.DrawCards;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class DisparoGelido {
    public void execute(Player player, EnemyInGame enemy) {
        //new Attack(1, player, enemy).execute();
        new DrawCards(1, player).executeAction();
        new DenyDamageEnemy(enemy).executeAction();
    }
}
