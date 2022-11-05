package org.springframework.samples.petclinic.statistic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoricRepository extends CrudRepository<Historic, Integer> {
    Optional<Historic> findByName(Integer integer);

    Optional<Historic> findById(int id);

    List<Historic> findAll();
}
