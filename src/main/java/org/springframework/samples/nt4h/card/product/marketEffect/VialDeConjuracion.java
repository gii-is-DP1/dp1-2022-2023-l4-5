package org.springframework.samples.nt4h.card.product.marketEffect;

import org.springframework.samples.nt4h.action.RecoverCard;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class VialDeConjuracion {
    public void execute(Player player) {
        new RecoverCard(player).executeAction();
        new DrawCards(1, player).executeAction();
    }
}
