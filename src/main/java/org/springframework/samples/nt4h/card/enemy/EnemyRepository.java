package org.springframework.samples.nt4h.card.enemy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EnemyRepository extends CrudRepository<Enemy, Integer> {

    @Query("SELECT e FROM Enemy e WHERE e.isNightLord IS TRUE")
    List<Enemy> findAllNightLords();

    @Query("SELECT e FROM Enemy e WHERE e.isNightLord IS FALSE")
    List<Enemy> findAllNotNightLords();
    List<Enemy> findAll();
}
