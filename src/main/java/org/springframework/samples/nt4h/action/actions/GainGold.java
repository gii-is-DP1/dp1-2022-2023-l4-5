package org.springframework.samples.nt4h.action.actions;

import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.player.Player;

public class GainGold implements Action {

    private Integer gold;
    private Player activePlayer;

    @Override
    public void executeAction() {
        Integer currentGold = activePlayer.getGold();
        if(gold>=0 || currentGold>=Math.abs(gold)){
            activePlayer.setGold(currentGold + gold);
        } else {
            activePlayer.setGold(0);
        }
    }
}
