package org.springframework.samples.nt4h.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Integer> {
    Optional<Game> findById(Integer id);

    List<Game> findAll();

    Page<Game> findAll(Pageable page);

}
