package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.player.Player;
@AllArgsConstructor
public class HealWound implements Action {

    private Player playerFrom;

    @Override
    public void executeAction() {
        if (playerFrom.getWounds() >= 1) {
            playerFrom.setWounds(playerFrom.getWounds() - 1);
        }
    }
}
