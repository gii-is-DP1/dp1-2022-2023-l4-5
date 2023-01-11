package org.springframework.samples.nt4h.achievement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer> {

    // TODO: @Cacheable permite guardar info.
    Optional<Achievement> findByName(String name);

    Optional<Achievement> findById(int id);

    List<Achievement> findAll();
}

