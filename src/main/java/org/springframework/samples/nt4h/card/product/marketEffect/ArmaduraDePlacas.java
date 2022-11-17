package org.springframework.samples.nt4h.card.product.marketEffect;

import org.springframework.samples.nt4h.action.RecoverCard;
import org.springframework.samples.nt4h.player.Player;

import java.util.stream.IntStream;

public class ArmaduraDePlacas {
    public void execute(Player player) {
        IntStream.range(0, 4).forEach(i -> new RecoverCard(player).executeAction());
    }
}
