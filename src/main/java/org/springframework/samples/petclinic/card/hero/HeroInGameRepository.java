package org.springframework.samples.petclinic.card.hero;

import org.springframework.data.repository.CrudRepository;

public interface HeroInGameRepository extends CrudRepository<HeroInGame, Integer> {
}
