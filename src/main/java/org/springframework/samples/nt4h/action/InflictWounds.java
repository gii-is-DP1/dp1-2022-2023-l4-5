package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@AllArgsConstructor
public class InflictWounds implements Action {

    private Player playerTo;

    public void recoverCard() {
        List<AbilityInGame> discardPile = playerTo.getDeck().getInDiscard();
        if (discardPile.isEmpty())
            return;
        AbilityInGame recoveredCard = discardPile.get(0);
        discardPile.remove(recoveredCard);
        playerTo.getDeck().getInDeck().add(recoveredCard);
    }

    @Override
    public void executeAction() {
        // Recover all the cards
        int discardPileSize = playerTo.getDeck().getInDiscard().size();
        IntStream.range(0, discardPileSize).forEach(i -> recoverCard());

        // Shuffle the new abilityPile
        Collections.shuffle(playerTo.getDeck().getInDeck());

        // Give the player a wound
        playerTo.setWounds(playerTo.getWounds() + 1);

    }
}
