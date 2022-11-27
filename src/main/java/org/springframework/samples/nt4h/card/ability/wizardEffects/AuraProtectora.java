package org.springframework.samples.nt4h.card.ability.wizardEffects;

import org.springframework.samples.nt4h.action.DenyDamageEnemy;
import org.springframework.samples.nt4h.action.DiscardCards;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuraProtectora {
    public void execute(Player player) {
        Game game = player.getGame();
        Integer numDiscards = game.getActualOrcs().size();
        new DiscardCards(numDiscards, player).executeAction();
        List<EnemyInGame> enemies = game.getActualOrcs();
        for (EnemyInGame enemy : enemies) {
            new DenyDamageEnemy(enemy).executeAction();
        }
    }
}
