package org.springframework.samples.nt4h.action.actions;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;

@AllArgsConstructor
public class RecoverCard implements Action {

    private Player playerFrom;

    @Override
    public void executeAction() {
        List<AbilityInGame> discardPile = playerFrom.getInDiscard();
        if (discardPile.isEmpty())
            return;
        AbilityInGame recoveredCard = discardPile.get(0);
        discardPile.remove(recoveredCard);
        playerFrom.getInHand().add(recoveredCard);
    }
}
