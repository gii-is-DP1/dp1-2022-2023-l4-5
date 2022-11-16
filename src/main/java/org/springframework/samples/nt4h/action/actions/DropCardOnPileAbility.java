package org.springframework.samples.nt4h.action.actions;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
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

        List<AbilityInGame> inHand = playerFrom.getInHand();
        List<AbilityInGame> abilityPile = playerFrom.getInDeck();

        for (AbilityInGame card : inHand) {
            AbilityCardType cardEnum = card.getAbilityCardType();
            if (cardEnum == cardToReturn) {
                abilityPile.add(card);
                playerFrom.setInDeck(abilityPile);
                inHand.remove(card);
                playerFrom.setInHand(inHand);
                return;
            }
        }
    }
}

