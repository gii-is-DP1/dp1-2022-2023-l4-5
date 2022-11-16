package org.springframework.samples.nt4h.action;

import org.springframework.samples.nt4h.player.Player;

public class HealWound implements Action {

    private Player playerFrom;

    @Override
    public void executeAction() {
        if (playerFrom.getWounds() >= 1) {
            playerFrom.setWounds(playerFrom.getWounds() - 1);
        }
    }
}
