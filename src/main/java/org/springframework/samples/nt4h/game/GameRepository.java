package org.springframework.samples.nt4h.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.nt4h.player.Player;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Integer> {
    Optional<Game> findById(Integer id);

    List<Game> findAll();

    Page<Game> findAll(Pageable page);

    Optional<Game> findByName(String name);

    List<Game> findDistinctById(int id);

    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.game g WHERE g.id = :gameId")
    List<Player> findPlayersByGame(int gameId);

}
