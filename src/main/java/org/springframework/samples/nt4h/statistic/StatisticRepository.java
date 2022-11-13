package org.springframework.samples.nt4h.statistic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticRepository extends CrudRepository<Statistic, Integer> {
    Optional<Statistic> findById(int id);

    List<Statistic> findAll();
}
