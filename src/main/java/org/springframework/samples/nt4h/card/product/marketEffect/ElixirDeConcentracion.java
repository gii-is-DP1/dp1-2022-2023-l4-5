package org.springframework.samples.nt4h.card.product.marketEffect;

import org.springframework.samples.nt4h.action.DrawCards;
import org.springframework.samples.nt4h.player.Player;

import org.springframework.stereotype.Component;

@Component
public class ElixirDeConcentracion {
    public void execute(Player player) {
        new DrawCards(3, player).executeAction();
    }
}
