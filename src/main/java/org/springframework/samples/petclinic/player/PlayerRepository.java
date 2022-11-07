package org.springframework.samples.petclinic.player;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Optional<Player> findByName(String name);

    Optional<Player> findById(int id);

    List<Player> findAll();
}
