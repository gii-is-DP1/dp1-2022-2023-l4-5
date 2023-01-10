package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.player.Player;

@AllArgsConstructor
public class TakeCardFromAbilityPile implements Action {

    private Player player;

    @Override
    public void executeAction() {

    }

}
