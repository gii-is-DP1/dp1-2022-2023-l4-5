package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.player.Player;

public class LoseCards extends Effect {

    public static final Integer numCards = 1;
    public Phase phase = Phase.HERO_ATTACK;

    @Override
    public boolean useEffect(Player player) {

        for (int i = 0; i < numCards; i++) {
            var card = player.getInDeck().get(0);
            player.getInDeck().remove(card);
            player.getInDiscard().add(card);
        }
        return true;
    }

}
