package org.springframework.samples.nt4h.statistic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticRepository extends CrudRepository<Statistic, Integer> {
    Optional<Statistic> findById(int id);

    List<Statistic> findAll();

    @Query("SELECT st.numPlayedGames FROM Statistic st JOIN User u WHERE u.id = ?1")
    Integer numPlayedGamesByUserId(int userId);

    @Query("SELECT st.timePlayed FROM Statistic st JOIN User u WHERE u.id = ?1")
    Integer numMinutesPlayedByUserId(int userId);
}
