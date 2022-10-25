package org.springframework.nt4h.capacity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CapacityService {
    private final CapacityRepository capacityRepository;

    @Autowired
    public CapacityService(CapacityRepository capacityRepository) {
        this.capacityRepository = capacityRepository;
    }

    public Capacity findCapacityById(int id) {
        return capacityRepository.findById(id).orElse(null);
    }

    public void saveCapacity(Capacity capacity) {
        capacityRepository.save(capacity);
    }

    public void deleteCapacityById(int id) {
        capacityRepository.deleteById(id);
    }

    public Iterable<Capacity> findAllCapacities() {
        return capacityRepository.findAll();
    }

}
