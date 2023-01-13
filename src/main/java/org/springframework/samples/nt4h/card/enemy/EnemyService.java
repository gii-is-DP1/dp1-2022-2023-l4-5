package org.springframework.samples.nt4h.card.enemy;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EnemyService {
    private final EnemyInGameRepository enemyInGameRepository;
    private final EnemyRepository enemyRepository;
    private static final Integer NUM_NIGHTLORDS = 3;


    // EnemyInGame
    @Transactional(readOnly = true)
    public EnemyInGame getEnemyInGameById(int id) {
        return enemyInGameRepository.findById(id).orElse(new EnemyInGame());
    }

    @Transactional
    public void saveEnemyInGame(EnemyInGame enemyInGame) {
        enemyInGameRepository.save(enemyInGame);
    }

    @Transactional
    public Enemy getNightLord() {
        int randomNumber = (int) (Math.random() * NUM_NIGHTLORDS);
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
        if(numPlayers == 2) limitEnemies = 17;
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

    @Transactional(rollbackFor = Exception.class)
    public void increaseLife(EnemyInGame enemy) {
        enemy.setActualHealth(enemy.getActualHealth() + 1);
        saveEnemyInGame(enemy);
    }


}
