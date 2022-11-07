package org.springframework.samples.petclinic.card.enemy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnemyRepository extends CrudRepository<Enemy, Integer> {
    Optional<Enemy> findByName(String name);

    Optional<Enemy> findById(int id);

    List<Enemy> findAll();
}
