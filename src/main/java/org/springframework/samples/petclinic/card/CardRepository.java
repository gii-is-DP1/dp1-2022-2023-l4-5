package org.springframework.samples.petclinic.card;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {

    Card findCardByName (String name);
    List<Card> findAllCards();
    Optional<Card> findById(Integer id);
}
