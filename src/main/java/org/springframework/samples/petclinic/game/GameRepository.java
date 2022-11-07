package org.springframework.samples.petclinic.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Integer> {
    Optional<Game> findById(Integer id);

    List<Game> findAll();

    // Comprobar
    @Query("SELECT g FROM Game g LEFT JOIN FETCH Player p WHERE p.id = ?1")
    List<Game> findByPlayer(Integer playerId);

    List<Game> findByMode(Integer mode);
}
