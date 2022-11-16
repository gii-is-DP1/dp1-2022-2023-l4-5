package org.springframework.samples.nt4h.card.ability.wizard;

import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class TorrenteDeLuz {
    public void execute(Player player, EnemyInGame enemy) {
        Game game = player.getGame();
        new Attack(2, player, enemy).execute();
        new GainGlory(1, player).execute();
        game.getPlayers()
            .forEach(
                x -> IntStream.range(0, 2).forEach(i -> new RecoverCard(x).execute()));
    }
}
