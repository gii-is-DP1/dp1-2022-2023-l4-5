package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;


@AllArgsConstructor
public class DropCardFromHand implements Action {

    private Player playerFrom;
    private AbilityCardType cardPlayed;

    @Override
    public void executeAction() {
        List<AbilityInGame> hand = playerFrom.getDeck().getInHand();
        for (AbilityInGame card : hand) {
            AbilityCardType carta = card.getAbilityCardType();
            if (carta.equals(cardPlayed)) {
                hand.remove(card);
                break;
            }
        }
    }
}
