package org.springframework.samples.nt4h.card.ability.rangerEffects;


import org.springframework.samples.nt4h.action.actions.Attack;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class CompañeroLobo{

    // Previenes 2 puntos de daño
    public void execute(Player activePlayer, EnemyInGame targetedEnemy) {
        new Attack(2, activePlayer, targetedEnemy).executeAction();
        new PreventDamage(2, activePlayer).executeAction();
    }
}
