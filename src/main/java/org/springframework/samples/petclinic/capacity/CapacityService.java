package org.springframework.samples.petclinic.capacity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CapacityService {
    private final CapacityRepository capacityRepository;


    @Transactional(readOnly = true)
    public Capacity getCapacityById(int id) {
        return capacityRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Capacity getCapacityByStateCapacity(StateCapacity stateCapacity) {
        return capacityRepository.findByStateCapacity(stateCapacity).orElse(null);
    }

    @Transactional(readOnly = true)
    public Iterable<Capacity> findAllCapacities() {
        return capacityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void saveCapacity(Capacity capacity) {
        capacityRepository.save(capacity);
    }

    // TODO: Actualizar Capacity.

    @Transactional
    public void deleteCapacityById(int id) {
        capacityRepository.deleteById(id);
    }


}
