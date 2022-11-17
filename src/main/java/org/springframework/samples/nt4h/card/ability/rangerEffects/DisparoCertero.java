package org.springframework.samples.nt4h.card.ability.rangerEffects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.EndAttackPhase;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.stereotype.Component;

@Component
public class DisparoCertero {

    @Autowired
    GameService gameService;
    @Autowired
    TurnService turnService;

    // 3 de daño, pierdes 1 carta y finaliza el ataque
    public void execute(Player activePlayer, EnemyInGame targetedEnemy) {
        //new Attack(3, activePlayer, targetedEnemy).executeAction();
        new EndAttackPhase(turnService,activePlayer.getTurn().get(0).getId()).executeAction();
    }
}
