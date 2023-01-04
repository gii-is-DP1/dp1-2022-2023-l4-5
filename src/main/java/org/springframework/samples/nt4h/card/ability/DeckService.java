package org.springframework.samples.nt4h.card.ability;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.RemoveCardFromHandToDiscard;
import org.springframework.samples.nt4h.action.TakeCardFromAbilityPile;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DeckService {

    @Transactional
    public List<AbilityInGame> takeNewCard(Player player, Deck deck) {
        while(player.getDeck().getInHand().size() < 4) {
            Action takeNewCard = new TakeCardFromAbilityPile(deck);
            takeNewCard.executeAction();
        } return player.getDeck().getInHand();
    }

    @Transactional
    public List<AbilityInGame> removeAbilityCards(Integer cardId, Player player) {
        while(player.getDeck().getInHand().size() > 4) {
            Action removeToDiscard = new RemoveCardFromHandToDiscard(player.getDeck(), cardId);
            removeToDiscard.executeAction();
        } return player.getDeck().getInHand();
    }

}
