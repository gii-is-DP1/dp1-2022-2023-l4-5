package org.springframework.samples.nt4h.card.stage.stageEffect;

import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;
import java.util.Random;

public class LagrimasDeAradiel {

    public void execute(Player activePlayer, Player victimPlayer) {
        List<AbilityInGame> actualPlayerCards = activePlayer.getInHand();
        List<AbilityInGame> actualPlayerDiscard = activePlayer.getInDiscard();
        List<AbilityInGame> victimPlayerCards = victimPlayer.getInHand();
        List<AbilityInGame> victimPlayerDiscard = victimPlayer.getInDiscard();
        Integer chosenCard = (int) Math.random()*victimPlayerCards.size();
        activePlayer.setGlory(activePlayer.getGlory() - 1);
        victimPlayer.setGlory(victimPlayer.getGlory() + 1);

        actualPlayerCards.add(victimPlayerCards.get(chosenCard));
        Integer idChosenCard = victimPlayerCards.get(chosenCard).getId();
        victimPlayerCards.remove(chosenCard);
        if(actualPlayerDiscard.contains(idChosenCard)) {
            victimPlayerDiscard.add(actualPlayerDiscard.get(idChosenCard));
            actualPlayerDiscard.remove(idChosenCard);
        }

    }

}
