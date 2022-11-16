package org.springframework.samples.nt4h.action.actions;

import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;

public class DiscardCards implements Action {

    private Integer numDiscards;

    private Player playerFrom;

    @Override
    public void executeAction() {
        for (int i = 0; i < numDiscards; i++) {
            List<AbilityInGame> listAbilityPile = playerFrom.getAbilityPile();
            if (!listAbilityPile.isEmpty()) {
                AbilityInGame discardedCard = listAbilityPile.get(0);
                listAbilityPile.remove(discardedCard);
                if (listAbilityPile.isEmpty())
                    new GiveWoundCommand(playerFrom).execute();

                playerFrom.getDiscardPile().add(discardedCard);
            } else {
                break;
            }
        }
    }

}
