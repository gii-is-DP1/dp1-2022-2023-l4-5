package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.player.Player;

public class LoseCards extends Effect {

    private final Integer quantity = 1;

    @Override
    public boolean useEffect(Player player) {

        for (int i = 0; i < quantity; i++) {
            var card = player.getInDeck().get(0);
            player.getInDeck().remove(card);
            player.getInDiscard().add(card);
        }
        return true;
    }

}
