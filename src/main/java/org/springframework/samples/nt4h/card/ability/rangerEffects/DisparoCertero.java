package org.springframework.samples.nt4h.card.ability.rangerEffects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class DisparoCertero {

    @Autowired
    GameService gameService;

    // 3 de daño, pierdes 1 carta y finaliza el ataque
    public void execute(Player activePlayer, EnemyInGame targetedEnemy) {
        new Attack(3, activePlayer, targetedEnemy).executeAction();
        new EndAttackPhase(gameService, activePlayer).executeAction();
    }
}
