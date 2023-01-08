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
        List<AbilityInGame> handPile = player.getDeck().getInHand();
        List<AbilityInGame> abilityPile = player.getDeck().getInDeck();
        while (handPile.size() < 4 && abilityPile.size() > 0) {
            int lastCardFromPile = abilityPile.size() - 1;
            AbilityInGame card = abilityPile.get(lastCardFromPile);
            handPile.add(card);
            abilityPile.remove(card);
        }
        return player.getDeck().getInHand();
    }

    @Transactional(rollbackFor = TooManyAbilitiesException.class)
    public List<AbilityInGame> removeAbilityCards(Integer cardId, Player player) throws TooManyAbilitiesException {
        while (player.getDeck().getInHand().size() > 4) {
            List<AbilityInGame> handPile = player.getDeck().getInHand();
            List<AbilityInGame> discardPile = player.getDeck().getInDiscard();
            AbilityInGame cardToRemove = handPile.stream().filter(card -> card.getId().equals(cardId)).findFirst().orElseThrow(() -> new NotFoundException("Card not found"));
            handPile.remove(cardToRemove);
            discardPile.add(cardToRemove);
        }
        return player.getDeck().getInHand();
    }


}
