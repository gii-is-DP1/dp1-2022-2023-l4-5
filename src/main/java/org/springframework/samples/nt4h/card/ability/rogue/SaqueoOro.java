package org.springframework.samples.nt4h.card.ability.rogue;

import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaqueoOro {
    public void execute(Player player) {
        Game game = player.getGame();
        List<EnemyInGame> activeOrcs = game.getOrcs();
        Integer enemies = activeOrcs.size();
        new GiveGold(enemies * 2, player).execute();
    }
}
