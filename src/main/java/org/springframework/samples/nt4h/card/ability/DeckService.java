package org.springframework.samples.nt4h.card.ability;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.StatisticService;
import org.springframework.samples.nt4h.turn.exceptions.TooManyAbilitiesException;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;
    private final AbilityService abilityService;
    private final CacheManager cacheManager;
    private final StatisticService statisticService;
    private final UserService userService;
    private final EnemyService enemyService;
    private final PlayerService playerService;
    private final GameService gameService;

    @Transactional(rollbackFor = Exception.class)
    public void saveDeck(Deck deck) {
        deckRepository.save(deck);
    }


    // Pierde una carta.
    @Transactional(rollbackFor = Exception.class)
    public void fromDeckToDiscard(Deck deck) {
        AbilityInGame inDeck = deck.getInDeck().get(0);
        deck.getInDeck().remove(inDeck);
        deck.getInDiscard().add(inDeck);
        saveDeck(deck);
    }

    // Perder una carta.
    @Transactional(rollbackFor = Exception.class)
    public void specificCardFromDeckToDiscard(Deck deck, AbilityInGame inDeck) {
        abilityService.saveAbilityInGame(inDeck);
        deck.getInDeck().remove(inDeck);
        deck.getInDiscard().add(inDeck);
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void loseCards(Deck deck, Integer times) {
        for (int i = 0; i < times; i++) {
            fromDiscardToDeck(deck);
        }
    }

    // Roba un carta.
    @Transactional(rollbackFor = Exception.class)
    public void fromDiscardToDeck(Deck deck) {
        AbilityInGame inDiscard = deck.getInDiscard().get(0);
        abilityService.saveAbilityInGame(inDiscard);
        deck.getInDiscard().remove(inDiscard);
        deck.getInDeck().add(inDiscard);
    }

    @Transactional(rollbackFor = Exception.class)
    public void specificCardFromHandToDiscard(Deck deck, AbilityInGame inHand) {
        abilityService.saveAbilityInGame(inHand);
        deck.getInHand().remove(inHand);
        deck.getInDiscard().add(inHand);
        saveDeck(deck);
    }

    // Roba una carta.
    @Transactional(rollbackFor = Exception.class)
    public void specificCardFromDiscardToDeck(Deck deck, AbilityInGame inDiscard) {
        abilityService.saveAbilityInGame(inDiscard);
        deck.getInDiscard().remove(inDiscard);
        deck.getInDeck().add(inDiscard);
        saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void retrievesCards(Deck deck, Integer times) {
        for (int i = 0; i < times; i++) {
            fromDiscardToDeck(deck);
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
        saveDeck(deck);
        return added;
    }

    @Transactional()
    public void attackEnemies(AbilityInGame usedAbility, HttpSession session, Player player, Game game, Integer userId) {
        if(!(usedAbility.getAttack() == 0)) {
            Integer damageToEnemy = usedAbility.getAttack() + cacheManager.getSharpeningStone(session) + cacheManager.getAttack(session);
            EnemyInGame attackedEnemy = cacheManager.getAttackedEnemy(session);
            Integer enemyInitialHealth = attackedEnemy.getActualHealth();
            attackedEnemy.setActualHealth(enemyInitialHealth - damageToEnemy);
            enemyService.saveEnemyInGame(attackedEnemy);
            List<EnemyInGame> otherAffectedEnemies = cacheManager.getEnemiesAlsoAttacked(session);
            for(int e = 0; otherAffectedEnemies.size() > e; e++) {
                EnemyInGame affectedEnemy = otherAffectedEnemies.get(e);
                List<EnemyInGame> moreDamageToEnemies = cacheManager.getEnemiesThatReceiveMoreDamage(session);
                Integer extraDamage = (moreDamageToEnemies.contains(affectedEnemy)) ? 1:0;
                Integer initialEnemy2Health = affectedEnemy.getActualHealth();
                affectedEnemy.setActualHealth(initialEnemy2Health - (damageToEnemy + extraDamage));
                statisticService.damageDealt(player, (damageToEnemy + extraDamage));
                enemyService.saveEnemyInGame(affectedEnemy);

                otherAffectedEnemies.add(attackedEnemy);
                gameService.deleteKilledEnemy(session, otherAffectedEnemies, game, player, userId);
            } 
        }
    }

}
