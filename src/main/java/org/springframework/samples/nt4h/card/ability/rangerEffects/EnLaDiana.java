package org.springframework.samples.nt4h.card.ability.rangerEffects;


import org.springframework.samples.nt4h.action.GainGlory;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;
import org.springframework.samples.nt4h.action.actions.DiscardCards;

@Component
public class EnLaDiana {

    // 4 de daño, ganas 1 de gloria y pierdes 1 carta
    public void execute(Player activePlayer, EnemyInGame targetedEnemy) {
        //new Attack(4, activePlayer, targetedEnemy).executeAction();
        new GainGlory(1, activePlayer).executeAction();
        new DiscardCards(1, activePlayer).executeAction();
    }
}
