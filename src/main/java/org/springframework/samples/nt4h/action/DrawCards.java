package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;

@AllArgsConstructor
public class DrawCards implements Action {

    private Integer amountCards;

    private Player player;

    @Override
    public void executeAction() {
        for (int i = 0; i < amountCards; i++) {
            List<AbilityInGame> abilities = player.getDeck().getInDeck();
            if (!abilities.isEmpty()) {
                AbilityInGame drawnCard = abilities.get(0);

                abilities.remove(drawnCard);
                if (abilities.isEmpty())
                    new ReceiveWound(player).executeAction();

                player.getDeck().getInHand().add(drawnCard);
            } else {
                break;
            }
        }
    }
}
