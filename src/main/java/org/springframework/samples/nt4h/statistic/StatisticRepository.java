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

    @Query("SELECT count(st) FROM Statistic st WHERE (st.tipoEnt = 'GAME' AND st.numPlayers = ?1)")
    Integer numPlayerPerGame(int numP);

    @Query("SELECT st.gold FROM Statistic st JOIN User u WHERE u.id = ?1")
    Integer NumGoldByPlayerId(int userId);

    @Query("SELECT st.glory FROM Statistic st JOIN User u WHERE u.id = ?1")
    Integer NumGloryByPlayerId(int userId);

    @Query("SELECT st.numOrcsKilled FROM Statistic st JOIN User u WHERE u.id = ?1")
    Integer NumOrcsByPlayerId(int userId);

    @Query("SELECT st.numWarLordKilled FROM Statistic st JOIN User u WHERE u.id = ?1")
    Integer NumWarLordByPlayerId(int userId);

    @Query("SELECT st.damageDealt FROM Statistic st JOIN User u WHERE u.id = ?1")
    Integer NumDamageByPlayerId(int userId);

    @Query("SELECT st.numWonGames FROM Statistic st JOIN User u WHERE u.id = ?1")
    Integer NumWonGamesByPlayerId(int userId);

}
