package org.springframework.samples.nt4h.action.actions;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
public class ReceiveWound implements Action {

    private Player player;


    @Override
    public void executeAction() {
        // Recover all the cards
        Integer discardPileSize = player.getInDiscard().size();
        for (int i = 0; i < player.getInDiscard().size(); i++) {
            Object recoverCards = new RecoverCommand(player).executeAction();
        }
        // Give the player a wound
        player.setWounds(player.getWounds() + 1);
        Set<HeroInGame> heroes = player.getHeroes();
            

        // Shuffle the new abilityPile
        Collections.shuffle(player.getInDeck());

    }

}
