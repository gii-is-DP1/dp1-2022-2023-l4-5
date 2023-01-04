package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.player.Player;

import java.util.Collections;
import java.util.stream.IntStream;

@AllArgsConstructor
public class ReceiveWound implements Action {

    private Player player;

    @Override
    public void executeAction() {
        // Recover all the cards
        int discardPileSize = player.getDeck().getInDiscard().size();
        IntStream.range(0, discardPileSize).forEach(i -> new RecoverCard(player).executeAction());

        // Shuffle the new abilityPile
        Collections.shuffle(player.getDeck().getInDeck());

        // Give the player a wound
        player.setWounds(player.getWounds() + 1);
    }

}
