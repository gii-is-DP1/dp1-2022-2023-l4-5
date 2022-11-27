package org.springframework.samples.nt4h.card.ability.rangerEffects;

import org.springframework.samples.nt4h.action.ChangeEnemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class Supervivencia {


    // Cambias uno de los enemigos en el campo de batalla por la carta inferior del mazo de la Horda.
    public void execute(Player activePlayer, EnemyInGame targetedEnemy) {
        new ChangeEnemy(activePlayer.getTurn().get(0).getId(), targetedEnemy,activePlayer).executeAction();
    }
}
