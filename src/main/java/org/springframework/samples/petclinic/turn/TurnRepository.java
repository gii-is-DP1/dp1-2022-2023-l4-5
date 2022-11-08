package org.springframework.samples.petclinic.turn;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurnRepository extends CrudRepository<Turn,Integer> {

    List<Turn> findAll();

    @Query("SELECT  t FROM Turn t WHERE t.id = :id ")
    Optional<Turn> findById(Integer id);


}
