package org.springframework.samples.nt4h.card.enemy.orc;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrcRepository extends CrudRepository<Orc, Integer> {
    List<Orc> findByName(String name);

    Orc findById(int id);

    List<Orc> findAll();
}
