package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;

@AllArgsConstructor
public class TakeCardFromAbilityPile implements Action {

    private Player player;

    @Override
    public void executeAction() {
        List<AbilityInGame> handPile = player.getInHand();
        List<AbilityInGame> abilityPile = player.getInHand();
        Integer lastCardFromPile = abilityPile.size()-1;
        handPile.add(abilityPile.get(lastCardFromPile));
        abilityPile.remove(lastCardFromPile);
    }

}