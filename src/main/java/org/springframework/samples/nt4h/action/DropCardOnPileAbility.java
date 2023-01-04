package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;

@AllArgsConstructor
public class DropCardOnPileAbility implements Action {


    private Player playerFrom;
    private AbilityCardType cardToReturn;

    @Override
    public void executeAction() {

        List<AbilityInGame> inHand = playerFrom.getDeck().getInHand();
        List<AbilityInGame> abilityPile = playerFrom.getDeck().getInDeck();

        for (AbilityInGame card : inHand) {
            AbilityCardType cardEnum = card.getAbilityCardType();
            if (cardEnum == cardToReturn) {
                abilityPile.add(card);
                playerFrom.getDeck().setInDeck(abilityPile);
                inHand.remove(card);
                playerFrom.getDeck().setInHand(inHand);
                return;
            }
        }
    }
}

