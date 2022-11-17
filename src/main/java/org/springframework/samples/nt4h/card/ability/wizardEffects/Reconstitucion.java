package org.springframework.samples.nt4h.card.ability.wizardEffects;

import org.springframework.samples.nt4h.action.DrawCards;
import org.springframework.samples.nt4h.action.RecoverCard;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class Reconstitucion {
    public void execute(Player player) {
            new DrawCards(1, player).executeAction();
        IntStream.range(0, 2).forEach(i -> new RecoverCard(player).executeAction());
    }
}
