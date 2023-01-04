package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.Deck;

import java.util.List;

@AllArgsConstructor
public class TakeCardFromAbilityPile implements Action {

    private Deck deck;

    @Override
    public void executeAction() {
        List<AbilityInGame> handPile = deck.getInHand();
        List<AbilityInGame> abilityPile = deck.getInHand();
        Integer lastCardFromPile = abilityPile.size()-1;
        // TODO: corregi, seguro que peta.
        handPile.add(abilityPile.get(lastCardFromPile));
        abilityPile.remove(lastCardFromPile);
    }

}
