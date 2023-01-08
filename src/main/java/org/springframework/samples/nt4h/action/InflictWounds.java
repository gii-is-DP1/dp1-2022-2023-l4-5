package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.Collections;
import java.util.stream.IntStream;

@AllArgsConstructor
public class InflictWounds implements Action {

    private Player playerTo;

    @Override
    public void executeAction() {
        // Recover all the cards
        int discardPileSize = playerTo.getDeck().getInDiscard().size();
        for (int i = 0; i < discardPileSize; i++) {
            AbilityInGame recoveredCard = playerTo.getDeck().getInDiscard().get(0);
            playerTo.getDeck().getInDiscard().remove(recoveredCard);
            playerTo.getDeck().getInDeck().add(recoveredCard);
        }

        // Shuffle the new abilityPile
        Collections.shuffle(playerTo.getDeck().getInDeck());

        // Give the player a wound
        playerTo.setWounds(playerTo.getWounds() + 1);


    }
}
