package org.springframework.samples.petclinic.capacity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<Capacity> getAllCapacities() {
        return capacityRepository.findAll();
    }

    @Transactional
    public void saveCapacity(Capacity capacity) {
        capacityRepository.save(capacity);
    }

    @Transactional
    public void deleteCapacityById(int id) {
        capacityRepository.deleteById(id);
    }

    @Transactional
    public void deleteCapacity(Capacity capacity) {
        capacityRepository.delete(capacity);
    }

    @Transactional(readOnly = true)
    public boolean capacityExists(int id) {
        return capacityRepository.existsById(id);
    }


}
