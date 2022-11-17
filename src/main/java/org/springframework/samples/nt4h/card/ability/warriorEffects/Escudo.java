package org.springframework.samples.nt4h.card.ability.warriorEffects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.DenyDamageEnemy;
import org.springframework.samples.nt4h.action.EndAttackPhase;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.stereotype.Component;

@Component
public class Escudo {
    @Autowired
    GameService gameService;
    @Autowired
    TurnService turnService;

    public void execute(Player player, EnemyInGame enemy) {
        new DenyDamageEnemy(enemy).executeAction();
        new EndAttackPhase(turnService, player.getTurn().get(0).getId()).executeAction();
    }
}
