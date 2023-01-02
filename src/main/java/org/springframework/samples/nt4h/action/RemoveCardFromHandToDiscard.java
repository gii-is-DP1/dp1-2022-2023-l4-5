package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.Deck;

import java.util.List;

@AllArgsConstructor
public class RemoveCardFromHandToDiscard implements Action {

    private Deck deck;
    private Integer idCard;

    @Override
    public void executeAction() {
        List<AbilityInGame> handPile = deck.getInHand();
        List<AbilityInGame> discardPile = deck.getInDiscard();
        List<AbilityInGame> abilityPile = deck.getInDeck();
        if(handPile.size() > 0) {
            // TODO: corregi, seguro que peta.
            handPile.remove(idCard);
            discardPile.add(handPile.get(idCard));
        }
    }
}
