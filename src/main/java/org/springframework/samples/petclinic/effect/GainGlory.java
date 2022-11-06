package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.player.Player;

public class GainGlory extends Effect {
    private final int glory = 2;
    Phase phase = Phase.HERO_ATTACK;

    @Override
    public boolean useEffect(Player player) {
        player.setGlory(player.getGlory() + glory);
        return true;
    }
}
