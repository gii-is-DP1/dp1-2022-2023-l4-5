package org.springframework.samples.nt4h.action.actions;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.player.Player;

@AllArgsConstructor
public class GainGlory implements Action {

    private Integer glory;
    private Player activePlayer;

    @Override
    public void executeAction() {
        Integer currentGlory = activePlayer.getGlory();
        if(glory>=0 || currentGlory>=Math.abs(glory)){
            activePlayer.setGlory(currentGlory + glory);
        } else {
            activePlayer.setGlory(0);
        }
    }
}
