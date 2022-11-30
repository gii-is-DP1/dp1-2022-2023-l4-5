package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;

@AllArgsConstructor
public class RemoveCardFromHandToDiscard implements Action {

    private Player player;
    private Integer idCard;

    @Override
    public void executeAction() {
        List<AbilityInGame> handPile = player.getInHand();
        List<AbilityInGame> discardPile = player.getInDiscard();
        handPile.remove(idCard);
        discardPile.add(handPile.get(idCard));
    }
}
