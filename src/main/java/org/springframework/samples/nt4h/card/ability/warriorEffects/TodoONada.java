package org.springframework.samples.nt4h.card.ability.warriorEffects;

import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class TodoONada {
    public void execute(Player player, EnemyInGame enemy){
        Integer extraDamage = player.getInDeck().get(0).getAttack();
        new Attack(1+extraDamage, player, enemy).executeAction();
    }
}
