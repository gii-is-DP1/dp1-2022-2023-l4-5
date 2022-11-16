package org.springframework.samples.nt4h.card.ability.warrior;

import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class VozDeAliento {
    public void execute(Player player) {
        Game game = player.getGame();
        new DrawCards(1, player).execute();
        new GainGlory(1, player).execute();
        game.getPlayers().forEach(x -> IntStream.range(0, 2).forEach(i -> new RecoverFirstFromDiscard(x).execute()));
    }
}
