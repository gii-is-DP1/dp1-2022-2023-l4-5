package org.springframework.samples.nt4h.card.enemy;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EnemyRepository extends CrudRepository<Enemy, Integer> {

    List<Enemy> findAll();

    public List<Enemy> findAllByIsNightLord(Boolean isNightLord);

}
