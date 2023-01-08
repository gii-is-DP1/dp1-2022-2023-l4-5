package org.springframework.samples.nt4h.card.ability;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.RemoveCardFromHandToDiscard;
import org.springframework.samples.nt4h.action.TakeCardFromAbilityPile;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.exceptions.TooManyAbilitiesException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DeckService {

    @Transactional()
    public List<AbilityInGame> takeNewCard(Player player) {
        while(player.getDeck().getInHand().size() < 4) {
            Action takeNewCard = new TakeCardFromAbilityPile(player);
            takeNewCard.executeAction();
        } return player.getDeck().getInHand();
    }

    @Transactional(rollbackFor = TooManyAbilitiesException.class)
    public List<AbilityInGame> removeAbilityCards(Integer cardId, Player player) throws TooManyAbilitiesException {
        while(player.getDeck().getInHand().size() > 4) {
            Action removeToDiscard = new RemoveCardFromHandToDiscard(player, cardId);
            removeToDiscard.executeAction();
        } if(player.getDeck().getInHand().size() > 4)
            throw new TooManyAbilitiesException();
        return player.getDeck().getInHand();
    }

}
