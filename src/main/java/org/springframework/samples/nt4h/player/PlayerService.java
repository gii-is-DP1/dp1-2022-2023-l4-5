package org.springframework.samples.nt4h.player;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.*;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AbilityService abilityService;
    private final TurnService turnService;
    private final UserRepository userRepository;


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

    @Transactional
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

    @Transactional
    public void addDeckFromRole(Player player, Mode mode) {
        Role[] roles = player.getHeroes().stream().map(h -> h.getHero().getRole()).distinct().toArray(Role[]::new);
        if (roles.length == 1 && mode == Mode.UNI_CLASS)
            createDeck(player, abilityService.getAbilitiesByRole(roles[0]), null);
        else if (roles.length == 2 && mode == Mode.MULTI_CLASS) {
            createDeck(player, abilityService.getAbilitiesByRole(roles[0]), 8);
            createDeck(player, abilityService.getAbilitiesByRole(roles[1]), 7);
        }
    }

    @Transactional
    void createDeck(Player player, List<Ability> abilities, Integer limit) {
        List<Ability> totalAbilities = Lists.newArrayList();
        Deck deck = player.getDeck();
        for (var ability : abilities)
            for (int i = 0; i < ability.getQuantity(); i++)
                totalAbilities.add(ability);
        Collections.shuffle(totalAbilities);
        for (int i = 0;(limit == null || i < limit) && i < totalAbilities.size(); i++) {
            Ability ability = totalAbilities.get(i);
            AbilityInGame abilityInGame = AbilityInGame.fromAbility(ability, player);
            abilityService.saveAbilityInGame(abilityInGame);
            if ((i < 5 && player.getHeroes().size() == 1) ||
                (deck.getInDeck().isEmpty() && i < 3 && player.getHeroes().size() == 2) ||
                (i < 2 && player.getHeroes().size() == 2))
                deck.getInHand().add(abilityInGame);
            else
                deck.getInDeck().add(abilityInGame);
        }
    }


    @Transactional
    public List<EnemyInGame> addNewEnemiesToBattle(List<EnemyInGame> enemies, List<EnemyInGame> allOrcs, Game game) {
        if (enemies.size() == 1 || enemies.size() == 2) {
            enemies.add(allOrcs.get(1));
            allOrcs.remove(1);
        } else if (enemies.size() == 0) {
            List<EnemyInGame> newEnemies = game.getAllOrcsInGame().stream().limit(3).collect(Collectors.toList());
            allOrcs.removeAll(newEnemies);
        }
        return allOrcs;
    }

    @Transactional
    public void restoreEnemyLife(List<EnemyInGame> enemies) {

        for (EnemyInGame enemyInGame : enemies) {
            Enemy enemy = enemyInGame.getEnemy();
            if (enemy.getHasCure()) {
                Action recoverEnemyLife = new HealEnemy(enemyInGame);
                recoverEnemyLife.executeAction();
            }
        }
    }
}
