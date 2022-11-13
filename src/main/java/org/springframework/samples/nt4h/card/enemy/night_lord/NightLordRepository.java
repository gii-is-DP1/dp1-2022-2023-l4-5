package org.springframework.samples.nt4h.card.enemy.night_lord;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NightLordRepository extends CrudRepository<NightLord, Integer> {
    Optional<NightLord> findByName(String name);

    Optional<NightLord> findById(int id);

    List<NightLord> findAll();
}
