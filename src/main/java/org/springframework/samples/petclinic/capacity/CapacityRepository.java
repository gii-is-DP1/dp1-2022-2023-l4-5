package org.springframework.samples.petclinic.capacity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapacityRepository extends CrudRepository<Capacity, Integer> {
    Optional<Capacity> findByStateCapacity(String name);

    Optional<Capacity> findById(int id);
}
