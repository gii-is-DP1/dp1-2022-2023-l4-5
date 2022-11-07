package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.player.Player;

public class RetrieveCards extends Effect {

    private final Integer quantity = 1;

    @Override
    public boolean useEffect(Player player) {

        for (int i = 0; i < quantity; i++) {
            var abilityInGame = player.getInDiscard().get(0);
            player.getInDiscard().remove(abilityInGame);
            player.getInDeck().add(abilityInGame);
        }
        return true;
    }
}
