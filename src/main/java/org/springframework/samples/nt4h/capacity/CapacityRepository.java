package org.springframework.samples.nt4h.capacity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CapacityRepository extends CrudRepository<Capacity, Integer> {
    List<Capacity> findByStateCapacity(StateCapacity stateCapacity);

    Optional<Capacity> findById(int id);

    List<Capacity> findAll();
}
