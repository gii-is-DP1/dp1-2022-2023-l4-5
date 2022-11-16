package org.springframework.samples.nt4h.card.ability.wizard;

import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class Reconstitucion {
    public void execute(Player player) {
            new DrawCards(1, player).execute();
        IntStream.range(0, 2).forEach(i -> new RecoverCard(player).execute());
    }
}
