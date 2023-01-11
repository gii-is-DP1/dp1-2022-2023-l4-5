package org.springframework.samples.nt4h.card.ability;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.turn.exceptions.TooManyAbilitiesException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class DeckService {

    //private final PlayerService playerService;
    private final DeckRepository deckRepository;
    private final AbilityService abilityService;

    @Transactional(rollbackFor = Exception.class)
    public void saveDeck(Deck deck) {
        deckRepository.save(deck);
    }


    // Pierde una carta.
    @Transactional(rollbackFor = Exception.class)
    public void loseACard(Deck deck) {
        AbilityInGame inDeck = deck.getInDeck().get(0);
        deck.getInDeck().remove(inDeck);
        deck.getInDiscard().add(inDeck);
        saveDeck(deck);
    }

    // Perder una carta.
    @Transactional(rollbackFor = Exception.class)
    public void loseTheCard(Deck deck, AbilityInGame inDeck) {
        abilityService.saveAbilityInGame(inDeck);
        deck.getInDeck().remove(inDeck);
        deck.getInDiscard().add(inDeck);
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void loseCards(Deck deck, Integer times) {
        for (int i = 0; i < times; i++) {
            retrievesACard(deck);
        }
    }

    // Roba un carta.
    @Transactional(rollbackFor = Exception.class)
    public void retrievesACard(Deck deck) {
        AbilityInGame inDiscard = deck.getInDiscard().get(0);
        abilityService.saveAbilityInGame(inDiscard);
        deck.getInDiscard().remove(inDiscard);
        deck.getInDeck().add(inDiscard);
    }

    // Roba una carta.
    @Transactional(rollbackFor = Exception.class)
    public void retrievesTheCard(Deck deck, AbilityInGame inDiscard) {
        abilityService.saveAbilityInGame(inDiscard);
        deck.getInDiscard().remove(inDiscard);
        deck.getInDeck().add(inDiscard);
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void retrievesCards(Deck deck, Integer times) {
        for (int i = 0; i < times; i++) {
            retrievesACard(deck);
        }
    }

    // Coloca la primera carta en la última posición.
    @Transactional(rollbackFor = Exception.class)
    public void putFirstCardAtBottomOfDeck(Deck deck) {
        AbilityInGame first = deck.getInDeck().get(0);
        deck.getInDeck().remove(first);
        deck.getInDeck().add(first);
        saveDeck(deck);
    }


    @Transactional(rollbackFor = Exception.class)
    public void restoreDeck(Deck deck) {
        for (int i = 0; i < deck.getInDiscard().size(); i++) {
            AbilityInGame recoveredCard = deck.getInDiscard().get(0);
            deck.getInDiscard().remove(recoveredCard);
            deck.getInDeck().add(recoveredCard);
        }
        // Shuffle the new abilityPile
        Collections.shuffle(deck.getInDeck());
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<AbilityInGame> moveCardsFromDeckToHand(Deck deck) throws TooManyAbilitiesException {
        if (deck.getInHand().size() > 4)
            throw new TooManyAbilitiesException();
        List<AbilityInGame> added = deck.getInDeck().subList(0, 5-deck.getInHand().size());
        deck.getInHand().addAll(added);
        deck.getInDeck().removeAll(added);
        saveDeck(deck);
        return added;
    }
}
