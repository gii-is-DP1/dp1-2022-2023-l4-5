package org.springframework.samples.petclinic.card.enemy;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EnemyInGameRepository extends CrudRepository<EnemyInGame, Integer> {
    Optional<EnemyInGame> findById(Integer id);

    List<EnemyInGame> findAll();
}
