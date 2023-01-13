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

    @Query("SELECT u.statistic.numPlayedGames FROM User u WHERE u.id = ?1")
    Integer findNumPlayedGamesByUser(int userId);


    @Query("SELECT u.statistic.timePlayed FROM User u WHERE u.id = ?1")
    Integer findNumMinutesPlayedByUser(int userId);

    @Query("SELECT count(g) FROM Game g WHERE g.statistic.numPlayers = ?1")
    Integer findNumGamesByNumPlayers(int numP);

    @Query("SELECT u.statistic.gold FROM User u WHERE u.id = ?1")
    Integer findNumGoldByUser(int userId);

    @Query("SELECT u.statistic.glory FROM User u WHERE u.id = ?1")
    Integer findNumGloryByUser(int userId);

    @Query("SELECT u.statistic.numOrcsKilled FROM User u WHERE u.id = ?1")
    Integer findNumOrcsByUser(int userId);

    @Query("SELECT u.statistic.numWarLordKilled FROM User u WHERE u.id = ?1")
    Integer findNumWarLordByUser(int userId);

    @Query("SELECT u.statistic.damageDealt FROM User u WHERE u.id = ?1")
    Integer findNumDamageByUser(int userId);

    @Query("SELECT u.statistic.numWonGames FROM User u WHERE u.id = ?1")
    Integer findNumWonGamesByUser(int userId);



}
