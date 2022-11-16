package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.player.Player;

@AllArgsConstructor
public class ProtectAlly implements Action {

    private Integer protect;

    private Player playerFrom;

    @Override
    public void executeAction() {
        if (playerFrom.getDamageProtect() < protect) {
            playerFrom.setDamageProtect(protect);
        }
    }

}
