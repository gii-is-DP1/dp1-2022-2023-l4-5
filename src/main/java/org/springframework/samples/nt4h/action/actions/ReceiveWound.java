package org.springframework.samples.nt4h.action.actions;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.player.Player;

@AllArgsConstructor
public class ReceiveWound implements Action {

    private Player player;

    @Override
    public void executeAction() {
        Integer discardPileSize = player.getInDiscard().size();
        for (int i = 0; i < player.getInDiscard().size(); i++) {
            Object recoverCards = new RecoverCommand(player).executeAction();
        }
        player.setWounds(player.getWounds() + 1);

        player.shuffleDeck();

    }

}
