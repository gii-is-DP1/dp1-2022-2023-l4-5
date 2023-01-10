package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.player.Player;

@AllArgsConstructor
public class GainGlory implements Action {

    private Integer glory;
    private Player activePlayer;

    @Override
    public void executeAction() {
        Integer currentGlory = activePlayer.getStatistic().getGlory();
        if(glory>=0 || currentGlory>=Math.abs(glory)){
            activePlayer.getStatistic().setGlory(currentGlory + glory);
        } else {
            activePlayer.getStatistic().setGlory(0);
        }
    }
}
