package org.springframework.samples.nt4h.card.ability.rogueEffects;

import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaqueoOroGloria {
    public void execute(Player player) {
        Game game = player.getGame();
        List<EnemyInGame> activeOrcs = game.getActualOrcs();
        Integer enemies = activeOrcs.size();
        new StealCoin(enemies, player).execute();
        new GainGlory(player).execute();
    }
}
