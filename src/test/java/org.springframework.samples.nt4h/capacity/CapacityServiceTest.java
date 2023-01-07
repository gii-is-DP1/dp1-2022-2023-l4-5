package org.springframework.samples.nt4h.capacity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CapacityServiceTest {

    @Autowired
    CapacityService capacityService;
    private StateCapacity stateCapacity;
    private final int idCapacity = 1;
    private int numsCapacities;

    @BeforeEach
    void setUp() {
        List<Capacity> capacities = capacityService.getAllCapacities();
        numsCapacities = capacities.size();
        stateCapacity = capacities.get(idCapacity-1).getStateCapacity();
    }

    @Test
    void testFindByStateCapacity() {
        List<Capacity> nuevo = capacityService.getCapacityByStateCapacity(stateCapacity);
        assertEquals(stateCapacity, nuevo.get(0).getStateCapacity());
    }

    @Test
    void testFindByCorrectId() {
        assertEquals(stateCapacity, capacityService.getCapacityById(idCapacity).getStateCapacity());
    }

    @Test
    void testFindByIncorrectId() {
        assertThrows(NotFoundException.class, () -> capacityService.getCapacityById(-1));
    }

    @Test
    void testFindAll() {
        assertEquals(numsCapacities, capacityService.getAllCapacities().size());
    }

    @Test
    void testExistsById(){
        assertTrue(capacityService.capacityExists(idCapacity));
        assertFalse(capacityService.capacityExists(-1));
    }


    @Test
    void testSave(){
        Capacity oldCapacity = Capacity.builder().stateCapacity(StateCapacity.EXPERTISE).lessDamage(false).build();
        capacityService.saveCapacity(oldCapacity);
        assertEquals(numsCapacities+1, capacityService.getAllCapacities().size());
    }
    @Test
    void testUpdate(){
        Capacity oldCapacity = capacityService.getCapacityById(idCapacity);
        Capacity newCapacity = oldCapacity.toBuilder().lessDamage(!oldCapacity.getLessDamage()).build();
        newCapacity.setId(idCapacity);
        capacityService.saveCapacity(newCapacity);
        assertEquals(newCapacity.getLessDamage(), capacityService.getCapacityById(idCapacity).getLessDamage());
    }
}
