package org.springframework.samples.nt4h.card.enemy;

import lombok.AllArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EnemyService {
    private final EnemyInGameRepository enemyInGameRepository;
    private final EnemyRepository enemyRepository;

    // EnemyInGame
    @Transactional(readOnly = true)
    public EnemyInGame getEnemyInGameById(int id) {
        return enemyInGameRepository.findById(id).orElseThrow(() -> new NotFoundException("EnemyInGame not found"));
    }

    @Transactional(readOnly = true)
    public List<EnemyInGame> getAllEnemyInGame() {
        return enemyInGameRepository.findAll();
    }

    @Transactional
    public void saveEnemyInGame(EnemyInGame enemyInGame) {
        enemyInGameRepository.save(enemyInGame);
    }

    @Transactional
    public void deleteEnemyInGame(EnemyInGame enemyInGame) {
        enemyInGameRepository.delete(enemyInGame);
    }

    @Transactional
    public void deleteEnemyInGameById(int id) {
        enemyInGameRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean enemyInGameExists(int id) {
        return enemyInGameRepository.existsById(id);
    }


    public List<Enemy> getAllEnemies() { return enemyRepository.findAll(); }

    public Enemy getNightLord() {
        Integer randomNumber = (int) (Math.random() * 3);
        List<Enemy> allNightLords = getAllEnemyByIsNightLord(true);
        return allNightLords.get(randomNumber);
    }

    @Transactional(readOnly = true)
    public List<Enemy> getAllEnemyByIsNightLord(Boolean isNightLord) {
        return enemyRepository.findAll().stream().filter(enemy -> enemy.getIsNightLord() == isNightLord).collect(Collectors.toList());
    }

}
