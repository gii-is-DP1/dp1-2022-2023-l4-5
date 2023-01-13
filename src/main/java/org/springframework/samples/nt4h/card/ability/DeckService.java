package org.springframework.samples.nt4h.card.ability;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.exceptions.TooManyAbilitiesException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;
    private final AbilityService abilityService;
    private final PlayerService playerService;
    private final Advise advise;
    private final Integer SIZE_HAND = 5;

    @Transactional(rollbackFor = Exception.class)
    public void saveDeck(Deck deck) {
        deckRepository.save(deck);
    }


    // De mazo a descarte.
    @Transactional(rollbackFor = Exception.class)
    public void fromDeckToDiscard(Player player, Deck deck) {
        AbilityInGame inDeck = deck.getInDeck().get(0);
        specificCardFromDeckToDiscard(player, deck, inDeck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void specificCardFromDeckToDiscard(Player player, Deck deck, AbilityInGame inDeck) {
        abilityService.saveAbilityInGame(inDeck);
        deck.getInDeck().remove(inDeck);
        deck.getInDiscard().add(inDeck);
        advise.fromDeckToDiscard(inDeck);
        if (deck.getInDeck().isEmpty())
            moveAllCardsFromDiscardToDeck(player, deck);
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void fromDeckToDiscard(Player player, Deck deck, Integer times) {
        for (int i = 0; i < times; i++) {
            fromDeckToDiscard(player, deck);
        }
    }

    // De descarte a mazo.
    @Transactional(rollbackFor = Exception.class)
    public void fromDiscardToDeck(Deck deck) {
        if (!deck.getInDiscard().isEmpty()) {
            AbilityInGame inDiscard = deck.getInDiscard().get(0);
            specificCardFromDiscardToDeck(deck, inDiscard);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void specificCardFromDiscardToDeck(Deck deck, AbilityInGame inDiscard) {
        abilityService.saveAbilityInGame(inDiscard);
        deck.getInDiscard().remove(inDiscard);
        deck.getInDeck().add(inDiscard);
        advise.fromDiscardToDeck(inDiscard);
        saveDeck(deck);
    }

    // De mazo a mano
    @Transactional(rollbackFor = Exception.class)
    public void fromDeckToHand(Player player, Deck deck) {
        AbilityInGame inDeck = deck.getInDeck().get(0);
        specificCardFromDeckToHand(player, deck, inDeck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void specificCardFromDeckToHand(Player player, Deck deck, AbilityInGame inDeck) {
        abilityService.saveAbilityInGame(inDeck);
        deck.getInDeck().remove(inDeck);
        deck.getInHand().add(inDeck);
        advise.fromDeckToHand(inDeck);
        if (deck.getInDeck().isEmpty())
            moveAllCardsFromDiscardToDeck(player, deck);
        saveDeck(deck);
    }

    // De descarte a mano
    @Transactional(rollbackFor = Exception.class)
    public void fromDiscardToHand(Deck deck) {
        System.out.println("fromDiscardToHand");
        if (!deck.getInDiscard().isEmpty()) {
            System.out.println("in fromDiscardToHand");
            AbilityInGame inDiscard = deck.getInDiscard().get(0);
            specificCardFromDiscardToHand(deck, inDiscard);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void specificCardFromDiscardToHand(Deck deck, AbilityInGame inDiscard) {
        System.out.println("specificCardFromDiscardToHand");
        abilityService.saveAbilityInGame(inDiscard);
        deck.getInDiscard().remove(inDiscard);
        deck.getInHand().add(inDiscard);
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void fromDiscardToHand(Deck deck, Integer times) {
        for (int i = 0; i < times; i++) {
            fromDiscardToHand(deck);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void specificCardFromHandToDiscard(Deck deck, AbilityInGame inHand) {
        abilityService.saveAbilityInGame(inHand);
        deck.getInHand().remove(inHand);
        deck.getInDiscard().add(inHand);
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void moveAllCardsFromDiscardToDeck(Player player, Deck deck) {
        List<AbilityInGame> tmp = new ArrayList<>(deck.getInDiscard());
        Collections.shuffle(tmp);
        deck.getInDiscard().clear();
        deck.getInDeck().addAll(tmp);
        saveDeck(deck);

        playerService.inflictWounds(player, 1);
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
    public void fromDeckToHand(Player player, Deck deck, Integer times) {
        for (int i = 0; i < times; i++) {
            fromDeckToHand(player, deck);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void moveCardsFromDeckToHand(Player player, Deck deck) throws TooManyAbilitiesException {
        if (deck.getInHand().size() > SIZE_HAND)
            throw new TooManyAbilitiesException();
        Integer cardsToMove = SIZE_HAND - deck.getInHand().size();
        fromDeckToHand(player, deck, cardsToMove);
    }

    @Transactional(rollbackFor = Exception.class)
    void createDeck(Player player, List<Ability> abilities, Integer limit) {
        List<Ability> totalAbilities = Lists.newArrayList();
        Deck deck = player.getDeck();
        for (var ability : abilities)
            for (int i = 0; i < ability.getQuantity(); i++) {
                totalAbilities.add(ability);
            }
        Collections.shuffle(totalAbilities);
        for (int i = 0;(limit == null || i < limit) && i < totalAbilities.size(); i++) {
            Ability ability = totalAbilities.get(i);
            AbilityInGame abilityInGame = AbilityInGame.fromAbility(ability, player);
            abilityService.saveAbilityInGame(abilityInGame);
            Boolean haveLessThanFiveAndIsUniclass = (i < SIZE_HAND && player.getHeroes().size() == 1);
            Boolean isTheFirstHeroInMulticlass = deck.getInDeck().isEmpty() && i < 3 && player.getHeroes().size() == 2;
            Boolean isTheSecondHeroInMUlticlass = (i < 2 && player.getHeroes().size() == 2);
            if (haveLessThanFiveAndIsUniclass || isTheFirstHeroInMulticlass || isTheSecondHeroInMUlticlass)
                deck.getInHand().add(abilityInGame);
            else
                deck.getInDeck().add(abilityInGame);

        }
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDeckFromRole(Player player, Mode mode) {
        Role[] roles = player.getHeroes().stream().map(h -> h.getHero().getRole()).distinct().toArray(Role[]::new);
        if (roles.length == 1 && mode == Mode.UNI_CLASS)
            createDeck(player, abilityService.getAbilitiesByRole(roles[0]), null);
        else if (roles.length == 2 && mode == Mode.MULTI_CLASS) {
            createDeck(player, abilityService.getAbilitiesByRole(roles[0]), 8);
            createDeck(player, abilityService.getAbilitiesByRole(roles[1]), 7);
        }
    }

    public void deleteAbilityInHand(Deck deck, AbilityInGame abilityInGame) {
        deck.getInHand().remove(abilityInGame);
        abilityService.deleteAbilityInGame(abilityInGame);
        saveDeck(deck);
    }
}
