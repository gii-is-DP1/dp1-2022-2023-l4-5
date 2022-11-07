package org.springframework.samples.petclinic.achievement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer> {
    Optional<Achievement> findByName(String name);

    Optional<Achievement> findById(int id);

    List<Achievement> findAll();
}

