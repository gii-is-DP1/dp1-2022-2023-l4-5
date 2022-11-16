package org.springframework.samples.nt4h.capacity;

import lombok.AllArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CapacityService {
    private final CapacityRepository capacityRepository;


    @Transactional(readOnly = true)
    public Capacity getCapacityById(int id) {
        return capacityRepository.findById(id).orElseThrow(() -> new NotFoundException("Capacity not found"));
    }

    @Transactional(readOnly = true)
    public List<Capacity> getCapacityByStateCapacity(StateCapacity stateCapacity) {
        return capacityRepository.findByStateCapacity(stateCapacity);
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
