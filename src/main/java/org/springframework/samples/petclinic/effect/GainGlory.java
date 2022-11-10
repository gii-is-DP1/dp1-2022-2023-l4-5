package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.player.Player;

public class GainGlory extends Effect {
    public static final int glory = 2;
    public Phase phase = Phase.HERO_ATTACK;

    @Override
    public boolean useEffect(Player player) {
        player.setGlory(player.getGlory() + glory);
        return true;
    }
}