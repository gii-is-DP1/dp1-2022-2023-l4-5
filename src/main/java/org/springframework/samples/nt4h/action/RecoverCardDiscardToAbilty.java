package org.springframework.samples.nt4h.action;


import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class RecoverCardDiscardToAbilty implements Action {


    private Player playerFrom;
    private AbilityCardType searchedCardType;


    @Override
    public void executeAction() {
        List<AbilityInGame> discardPile = playerFrom.getInDiscard();
        List<AbilityInGame> abilityPile = playerFrom.getInHand();

        AbilityInGame sameTypeCard = discardPile.stream()
            .filter(card -> card.getAbilityCardType().equals(searchedCardType))
            .findAny()
            .orElse(null);
        discardPile.remove(sameTypeCard);
        abilityPile.add(sameTypeCard);

        Collections.shuffle(abilityPile);
    }
}
