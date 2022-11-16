package org.springframework.samples.nt4h.card.ability.warrior;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class Escudo {
    @Autowired
    GameService gameService;

    public void execute(Player player, EnemyInGame enemy) {
        new DenyDamageEnemy(enemy).execute();
        new EndAttackFase(gameService, player).execute();
    }
}
