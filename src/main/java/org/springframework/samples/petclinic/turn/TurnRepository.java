package org.springframework.samples.petclinic.turn;


import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.effect.Phase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurnRepository extends CrudRepository<Turn, Integer> {

    List<Turn> findAll();

    Optional<Turn> findById(Integer id);

    List<Turn> findByPhase(Phase phase);


}
