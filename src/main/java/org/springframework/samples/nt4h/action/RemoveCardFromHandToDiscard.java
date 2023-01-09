package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;

@AllArgsConstructor
public class RemoveCardFromHandToDiscard implements Action {

    private Player player;
    private Integer idCard;

    @Override
    public void executeAction() {

    }

}
