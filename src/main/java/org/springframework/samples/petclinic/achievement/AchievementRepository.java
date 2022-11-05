package org.springframework.samples.petclinic.achievement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer> {
    java.util.Optional<Achievement> findByName(String name);

    java.util.Optional<Achievement> findById(int id);

    java.util.List<Achievement> findAll();
}

