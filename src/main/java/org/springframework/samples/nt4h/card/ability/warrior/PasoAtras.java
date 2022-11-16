package org.springframework.samples.nt4h.card.ability.warrior;

import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class PasoAtras {
    public void execute(Player player) {
        new DrawCards(2, player).execute();
    }
}
