package org.springframework.samples.petclinic.card.ability;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbilityInGameRepository extends CrudRepository<AbilityInGame, Integer> {
    Optional<AbilityInGame> findById(Integer id);

    List<AbilityInGame> findAll();

    @Query("SELECT a FROM AbilityInGame a WHERE a.player.id = ?1")
    List<Ability> findByPlayer(Integer id);
}