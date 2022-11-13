package org.springframework.samples.nt4h.card.ability;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbilityRepository extends CrudRepository<Ability, Integer> {

    Optional<Ability> findById(Integer id);

    List<Ability> findAll();

    Optional<Ability> findByName(String name);


}
