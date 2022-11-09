package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.player.Player;

public class StealCards extends Effect {

    public final Integer quantity = 1;

    @Override
    public boolean useEffect(Player player) {
        var abilityInGame = player.getInDeck().get(0);
        player.getInDeck().remove(abilityInGame);
        player.getInHand().add(abilityInGame);
        return true;
    }
}
