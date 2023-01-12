package org.springframework.samples.nt4h.card.ability;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.game.Mode;
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
    public void specificCardFromHandToDiscard(Deck deck, AbilityInGame inHand) {
        abilityService.saveAbilityInGame(inHand);
        deck.getInHand().remove(inHand);
        deck.getInDiscard().add(inHand);
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void specificCardFromDiscardToDeck(Deck deck, AbilityInGame inDiscard) {
        abilityService.saveAbilityInGame(inDiscard);
        deck.getInDiscard().remove(inDiscard);
        deck.getInDeck().add(inDiscard);
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void fromDiscardToDeck(Deck deck, Integer times) {
        for (int i = 0; i < times; i++) {
            fromDiscardToDeck(deck);
        }
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
        if (deck.getInDeck().isEmpty())
            moveAllCardsFromDiscardToDeck(player, deck);
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
        List<AbilityInGame> added = deck.getInDeck().subList(0, 5 - deck.getInHand().size());
        deck.getInHand().addAll(added);
        saveDeck(deck);
        return added;
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
            Boolean haveLessThanFiveAndIsUniclass = (i < 5 && player.getHeroes().size() == 1);
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
}
