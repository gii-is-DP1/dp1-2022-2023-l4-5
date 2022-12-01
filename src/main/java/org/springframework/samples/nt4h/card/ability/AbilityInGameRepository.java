package org.springframework.samples.nt4h.card.ability;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbilityInGameRepository extends CrudRepository<AbilityInGame, Integer> {
    Optional<AbilityInGame> findById(Integer id);

    List<AbilityInGame> findAll();

    void deleteAllByPlayer(Player player);
}
