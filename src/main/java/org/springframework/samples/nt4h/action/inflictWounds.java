package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.player.Player;

import java.util.Collections;
import java.util.stream.IntStream;

@AllArgsConstructor
public class inflictWounds implements Action{

    private Player playerTo;

    @Override
    public void executeAction() {
        // Recover all the cards
        Integer discardPileSize = playerTo.getInDiscard().size();
        IntStream.range(0, discardPileSize).forEach(i -> new RecoverCard(playerTo).executeAction());

        // Shuffle the new abilityPile
        Collections.shuffle(playerTo.getInDeck());

        // Give the player a wound
        playerTo.setWounds(playerTo.getWounds() + 1);
    }
}
