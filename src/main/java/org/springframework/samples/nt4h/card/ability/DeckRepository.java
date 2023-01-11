package org.springframework.samples.nt4h.card.ability;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeckRepository extends CrudRepository<Deck, Integer> {

    Optional<Deck> findById(Integer integer);

}
