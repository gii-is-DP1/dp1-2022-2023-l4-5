package org.springframework.samples.nt4h.card.ability.warriorEffects;

import org.springframework.samples.nt4h.action.DrawCards;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class PasoAtras {
    public void execute(Player player) {
        new DrawCards(2, player).executeAction();
    }
}
