package org.springframework.samples.nt4h.card.enemy;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EnemyService {
    private final EnemyInGameRepository enemyInGameRepository;
    private final EnemyRepository enemyRepository;
    private final DeckService deckService;


    // EnemyInGame
    @Transactional(readOnly = true)
    public EnemyInGame getEnemyInGameById(int id) {
        return enemyInGameRepository.findById(id).orElse(new EnemyInGame());
    }

    @Transactional(readOnly = true)
    public List<EnemyInGame> getAllEnemyInGame() {
        return enemyInGameRepository.findAll();
    }

    @Transactional
    public void saveEnemyInGame(EnemyInGame enemyInGame) {
        enemyInGameRepository.save(enemyInGame);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteEnemyInGame(EnemyInGame enemyInGame) {
        enemyInGame.onDeleteSetNull();
        enemyInGameRepository.save(enemyInGame);
        enemyInGameRepository.delete(enemyInGame);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteEnemyInGameById(int id) {
        EnemyInGame enemyInGame = getEnemyInGameById(id);
        deleteEnemyInGame(enemyInGame);
    }

    @Transactional(readOnly = true)
    public boolean enemyInGameExists(int id) {
        return enemyInGameRepository.existsById(id);
    }

    @Transactional
    public List<Enemy> getAllEnemies() { return enemyRepository.findAll(); }

    @Transactional
    public Enemy getNightLord() {
        int randomNumber = (int) (Math.random() * 3);
        List<Enemy> allNightLords = getAllNightLords();
        return allNightLords.get(randomNumber);
    }

    @Transactional(readOnly = true)
    public List<Enemy> getAllNightLords() {
        return enemyRepository.findAllNightLords();
    }

    @Transactional(readOnly = true)
    public List<Enemy> getAllNotNightLords() {
        return enemyRepository.findAllNotNightLords();
    }

    @Transactional
    public List<EnemyInGame> addOrcsToGame(Integer numPlayers) {
        int limitEnemies = 19;
        if(numPlayers == 2) limitEnemies = 19;
        else if(numPlayers == 3) limitEnemies = 23;
        else if(numPlayers == 4) limitEnemies = 27;
        List<EnemyInGame> orcs = getAllNotNightLords().stream().map(enemy -> EnemyInGame.createEnemy(false, enemy)).collect(Collectors.toList());
        Collections.shuffle(orcs);
        List<EnemyInGame> limitedOrcs = orcs.subList(0, limitEnemies);
        saveEnemyInGame(limitedOrcs.get(0));
        limitedOrcs.forEach(this::saveEnemyInGame);
        return limitedOrcs;
    }

    @Transactional
    public EnemyInGame addNightLordToGame() {
        Enemy nightLord = getNightLord();
        EnemyInGame nightLordInGame = EnemyInGame.createEnemy(true, nightLord);
        saveEnemyInGame(nightLordInGame);
        return nightLordInGame;
    }

    @Transactional
    public Integer attackEnemyToActualPlayer(Game game, HttpSession session, Predicate<EnemyInGame> hasPreventedDamage, int defendedDmg, List<EnemyInGame> enemiesInATrap) {
        Player currentPlayer = game.getCurrentPlayer();
        if (game.getActualOrcs().isEmpty()) return 0;
        int damage = game.getActualOrcs().stream()
            .filter(hasPreventedDamage)
            .mapToInt(EnemyInGame::getActualHealth).sum();
        int finalDamage = (damage >= defendedDmg) ? (damage - defendedDmg):damage;
        deckService.fromDeckToDiscard(currentPlayer, currentPlayer.getDeck(), damage);
        for (int e = 0; e <= enemiesInATrap.size(); e++) {
            enemiesInATrap.get(e).setActualHealth(0);
            game.getActualOrcs().remove(enemiesInATrap.get(e));
        }
        return finalDamage;
    }

    @Transactional(rollbackFor = Exception.class)
    public Enemy getEnemyById(int id) {
        return enemyRepository.findById(id).orElseThrow(() -> new NotFoundException("Enemy not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    public void increaseLife(EnemyInGame enemy) {
        enemy.setActualHealth(enemy.getActualHealth() + 1);
        saveEnemyInGame(enemy);
    }


}
