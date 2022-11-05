package org.springframework.samples.petclinic.card.enemy;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EnemyService {
    private final EnemyRepository enemyRepository;

    @Transactional(readOnly = true)
    public Enemy findEnemyByName(String name) {
        return enemyRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public Enemy findEnemyById(int id) {
        return enemyRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Enemy> findAllEnemies() {
        return enemyRepository.findAll();
    }

    @Transactional
    public void saveEnemy(Enemy enemy) {
        enemyRepository.save(enemy);
    }

    @Transactional
    public void deleteEnemy(Enemy enemy) {
        enemyRepository.delete(enemy);
    }

    @Transactional
    public void deleteEnemyById(int id) {
        enemyRepository.deleteById(id);
    }
}
