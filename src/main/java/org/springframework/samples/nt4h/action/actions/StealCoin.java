package org.springframework.samples.nt4h.action.actions;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.player.Player;


@AllArgsConstructor
public class StealCoin implements Action {

    private Player playerFrom;
    private Player playerTo;

    @Override
    public void executeAction() {
        if (playerTo.getGold() == 0)                  // Remove a coin from the player who is being robbed
            return;                                   // If the player has no coins, do nothing
        playerTo.setGold(playerTo.getGold() - 1);
        playerFrom.setGold(playerFrom.getGold() + 1); // And give it to the player who is robbing

    }

}
