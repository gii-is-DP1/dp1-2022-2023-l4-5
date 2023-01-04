package org.springframework.samples.nt4h.player;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.*;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createTurns(Player player) {
        turnService.createAllTurnForAPlayer(player);
    }

    @Transactional
    public void savePlayer(Player player) {
        playerRepository.save(player);
    }


    @Transactional
    public void deletePlayerById(int id) {
        Player player = getPlayerById(id);
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
            player.createDeck(player, abilityService.getAbilitiesByRole(roles[0]), roles[0].getAbilities().size() - 1);
        else if (roles.length == 2 && mode == Mode.MULTI_CLASS) {
            player.createDeck(player, abilityService.getAbilitiesByRole(roles[0]), 8);
            player.createDeck(player, abilityService.getAbilitiesByRole(roles[1]), 7);
        }
    }


    @Transactional
    public List<EnemyInGame> addNewEnemiesToBattle(List<EnemyInGame> enemies, List<EnemyInGame> allOrcs, Game game) {
        if (enemies.size() == 1 || enemies.size() == 2) {
            enemies.add(allOrcs.get(1));
            allOrcs.remove(1);
        } else if (enemies.size() == 0) {
            enemies = game.getAllOrcsInGame().stream().limit(3).collect(Collectors.toList());
            allOrcs.removeAll(enemies);
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
