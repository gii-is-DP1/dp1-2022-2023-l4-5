package org.springframework.samples.nt4h.player;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.*;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.turn.EnoughCardsException;
import org.springframework.samples.nt4h.turn.EnoughEnemiesException;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AbilityService abilityService;
    private final TurnService turnService;

    @Transactional(readOnly = true)
    public Player getPlayerById(int id) {
        return playerRepository.findById(id).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional
    public void savePlayer(Player player) {
        playerRepository.save(player);
        turnService.createAllTurnForAPlayer(player);
    }

    @Transactional
    public void deletePlayerById(int id) {
        playerRepository.deleteById(id);
    }

    @Transactional
    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Player getPlayerByName(String name) {
        return playerRepository.findByName(name).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional(readOnly = true)
    public boolean playerExists(int id) {
        return playerRepository.existsById(id);
    }

    @Transactional
    public void addDeckFromRole(Player player, Mode mode) {
        Role[] roles = player.getHeroes().stream().map(h -> h.getHero().getRole()).distinct().toArray(Role[]::new);
        if (roles.length == 1 && mode == Mode.UNI_CLASS)
            createDeck(player, roles[0], roles[0].getAbilities().size() - 1);
        else if (roles.length == 2 && mode == Mode.MULTI_CLASS) {
            createDeck(player, roles[0], 8);
            createDeck(player, roles[1], 7);
        }
    }

    private void createDeck(Player player, Role role, Integer limit) {
        List<Ability> abilities = abilityService.getAbilitiesByRole(role);
        List<Ability> totalAbilities = Lists.newArrayList();
        for (var ability : abilities)
            for (int i = 0; i < ability.getQuantity(); i++)
                totalAbilities.add(ability);
        Collections.shuffle(totalAbilities);
        for (int i = 0; i < limit; i++) {
            Ability ability = totalAbilities.get(i);
            AbilityInGame abilityInGame = AbilityInGame.builder()
                .player(player).ability(ability).timesUsed(0).attack(ability.getAttack()).isProduct(false).build();
            abilityService.saveAbilityInGame(abilityInGame);
            player.addAbilityInDeck(abilityInGame);
        }
    }

    @Transactional
    private void removeAbilityCards(Integer cardId, Player player) throws EnoughCardsException, NoMoneyException {
        while(player.getInHand().size() > 3) {
            if(player.getInHand().size() > 3) {
                Action removeToDiscard = new RemoveCardFromHandToDiscard(player, cardId);
                removeToDiscard.executeAction();
            } else
                throw new EnoughCardsException();
        }
    }

    @Transactional
    public void takeNewCard(Player player) throws EnoughCardsException, NoMoneyException {
        for(int i = 0; i < 3; i++) {
            if(player.getInHand().size() < 3) {
                Action takeNewCard = new TakeCardFromAbilityPile(player);
                takeNewCard.executeAction();
            } else
                throw new EnoughCardsException();
        }
    }

    @Transactional
    public void addNewEnemiesToBattle(List<EnemyInGame> enemies, List<EnemyInGame> allOrcs, Game game) throws EnoughEnemiesException {
        if(enemies.size() == 1 || enemies.size() == 2) {
            enemies.add(allOrcs.get(1));
            allOrcs.remove(1);
        } else if(enemies.size() == 0) {
            enemies = game.getAllOrcsInGame().stream().limit(3).collect(Collectors.toList());
            allOrcs.removeAll(enemies);
        } else
            throw new EnoughEnemiesException();
    }

    @Transactional
    public void restoreEnemyLife(List<EnemyInGame> enemies) throws NoMoneyException {
        for(int i = 0; enemies.size()<i; i++) {
            Action recoverEnemyLife = new HealEnemy(enemies.get(i));
            recoverEnemyLife.executeAction();
        }
    }

}
