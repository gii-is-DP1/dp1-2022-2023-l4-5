package org.springframework.samples.nt4h.card.enemy;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EnemyRepository extends CrudRepository<Enemy, Integer> {

    public List<Enemy> findAllByIsNightLord(Boolean isNightLord);
    public List<Enemy> findAll();
}
