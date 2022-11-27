package org.springframework.samples.nt4h.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.Turn;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Integer> {
    Optional<Game> findById(Integer id);

    List<Game> findAll();

    // Comprobar
    @Query("SELECT g FROM Game g LEFT JOIN FETCH Player p WHERE p.id = ?1")
    List<Game> findByPlayer(Integer playerId);

    List<Game> findByMode(Mode mode);


    Optional<Game> findByName(String name);

    @Query("SELECT g FROM Game g LEFT JOIN FETCH Turn t WHERE t.id = ?1")
    Turn findByTurn(int turnId);

    List<Game> findDistinctById(int id);

    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.game g WHERE g.id = :gameId")
    List<Player> findPlayersByGame(int gameId);

    @Query("SELECT p FROM Game g LEFT JOIN FETCH Player p LEFT JOIN FETCH User u WHERE p.name = u.username AND g.id = : gameId")
    Player findGameById(int gameId);


}
