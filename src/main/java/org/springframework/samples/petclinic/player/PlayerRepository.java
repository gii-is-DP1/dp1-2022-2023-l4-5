package org.springframework.samples.petclinic.player;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.card.ability.AbilityInGame;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    @Query("SELECT p FROM Player p WHERE p.name=:name")
    Optional<Player> findByName(String name);
    @Query("SELECT p FROM Player p WHERE p.id=:id")
    Optional<Player> findById(int id);

    List<Player> findAll();

    @Query("SELECT p FROM AbilityInGame p")
    List<AbilityInGame> getCardInHand();

    @Query("SELECT p FROM AbilityInGame p")
    List<AbilityInGame> getCardInDeck();

    @Query("SELECT p FROM AbilityInGame p")
    List<AbilityInGame> getCardInDiscard();
}
