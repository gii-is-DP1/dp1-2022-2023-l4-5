package org.springframework.samples.nt4h.card.ability.warriorEffects;

import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;
import org.springframework.samples.nt4h.action.actions.DiscardCards;
@Component
public class AtaqueBrutal {
    public void execute(Player player, EnemyInGame enemy) {
        new Attack(3, player, enemy).executeAction();
        new DiscardCards(1, player).executeAction();
    }
}
