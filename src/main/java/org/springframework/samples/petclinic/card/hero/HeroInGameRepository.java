package org.springframework.samples.petclinic.card.hero;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface HeroInGameRepository extends CrudRepository<HeroInGame, Integer> {

    @Query("SELECT hig FROM HeroInGame hig WHERE hig.id = :id")
    Optional<HeroInGame> findById(Integer id);

    List<HeroInGame> findAll();
}
