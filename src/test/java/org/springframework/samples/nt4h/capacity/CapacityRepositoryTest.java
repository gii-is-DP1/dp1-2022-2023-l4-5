package org.springframework.samples.nt4h.capacity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class CapacityRepositoryTest {
    @Autowired
    private CapacityRepository capacityRepository;

    private StateCapacity stateCapacity;
    private final int idCapacity = 1;

    private int numsCapacities;

    @BeforeEach
    void setup() {
        List<Capacity> capacities = capacityRepository.findAll();
        numsCapacities = capacities.size();
        stateCapacity = capacities.get(idCapacity-1).getStateCapacity();
    }

    @Test
    void testFindByStateCapacity() {
        assertEquals(stateCapacity, capacityRepository.findByStateCapacity(stateCapacity).get(0).getStateCapacity());
    }

    @Test
    void testFindAll() {
        assertEquals(numsCapacities, capacityRepository.findAll().size());
    }

    @Test
    void testDeleteByIncorrectId() {
        assertThrows(EmptyResultDataAccessException.class, () -> capacityRepository.deleteById(-1));
    }

    @Test
    void testFindById() {
        assertEquals(stateCapacity, capacityRepository.findById(idCapacity).get().getStateCapacity());
    }

    @Test
    void testFindByIncorrectId() {
        assertTrue(capacityRepository.findById(-1).isEmpty());
    }

    @Test
    void testDeleteAll() {
        capacityRepository.deleteAll();
        assertEquals(0, capacityRepository.findAll().size());
    }

    @Test
    void testUpdate() {
        Capacity capacity = capacityRepository.findById(idCapacity+1).get();
        capacity.setStateCapacity(stateCapacity);
        capacityRepository.save(capacity);
        assertEquals(stateCapacity, capacityRepository.findById(idCapacity).get().getStateCapacity());
    }
}

