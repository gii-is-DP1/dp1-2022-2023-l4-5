package org.springframework.samples.nt4h.achievement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer> {

    Optional<Achievement> findByName(String name);

    Optional<Achievement> findById(int id);

    List<Achievement> findAll();

    @Query("SELECT t FROM Achievement t WHERE t.achievementType= ?1")
    List<Achievement> findAchievementByType(AchievementType type);
}

