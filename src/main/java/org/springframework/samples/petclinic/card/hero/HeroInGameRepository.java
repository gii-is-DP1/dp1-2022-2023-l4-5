package org.springframework.samples.petclinic.card.hero;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface HeroInGameRepository extends CrudRepository<HeroInGame, Integer> {

    Optional<HeroInGame> findById(Integer id);

    List<HeroInGame> findAll();
}
