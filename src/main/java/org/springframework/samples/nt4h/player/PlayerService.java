package org.springframework.samples.nt4h.player;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.*;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AbilityService abilityService;
    private final TurnService turnService;
    private final UserRepository userRepository;
    private final DeckService deckService;


    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public Player getPlayerById(int id) {
        return playerRepository.findById(id).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional
    public void createTurns(Player player) {
        turnService.createAllTurnForAPlayer(player);
        savePlayer(player);
    }

    @Transactional
    public void savePlayer(Player player) {
        playerRepository.save(player);
    }


    @Transactional
    public void deletePlayerById(int id) {
        Player player = getPlayerById(id);
        playerRepository.findUserByPlayer(player).ifPresent(user -> {
            user.setPlayer(null);
            userRepository.save(user);
        });
        deletePlayer(player);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePlayer(Player player) {
        player.onDeleteSetNull();
        playerRepository.save(player);
        playerRepository.delete(player);
    }

    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public Player getPlayerByName(String name) {
        return playerRepository.findByName(name).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional(readOnly = true)
    public boolean playerExists(int id) {
        return playerRepository.existsById(id);
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
        deckService.saveDeck(deck);
    }

    @Transactional(rollbackFor = Exception.class)
    public void decreaseWounds(Player player, int wounds) {
        if (wounds > 0) {
            player.setWounds(player.getWounds() - wounds);
            savePlayer(player);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void inflictWounds(Player player, int i, Game game) {
        player.setWounds(player.getWounds() + i);
        if (player.getHealth() <= 0)
            player.setAlive(false);
        else
            deckService.restoreDeck(player.getDeck());
        savePlayer(player);

    }


}
