package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;
@AllArgsConstructor
public class DiscardCards implements Action {

    private Integer numberDiscards;
    private Player player;

    @Override
    public void executeAction() {
        for (int i = 0; i < numberDiscards; i++) {
            List<AbilityInGame> abilities = player.getDeck().getInDeck();
            if (!abilities.isEmpty()) {
                AbilityInGame removedCard = abilities.get(0);
                abilities.remove(removedCard);
                if (abilities.isEmpty())
                    new ReceiveWound(player).executeAction();
                player.getDeck().getInDiscard().add(removedCard);
            }
        }
    }
}


