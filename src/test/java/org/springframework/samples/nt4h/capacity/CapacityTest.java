package org.springframework.samples.nt4h.capacity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class CapacityTest {

    @Autowired
    private CapacityRepository capacityRepository;
    @Autowired
    private Validator validator;

    private Capacity capacity;

    @BeforeEach
    public void setup() {
        capacity = new Capacity();
        capacity.setStateCapacity(StateCapacity.MAGIC);
        capacity.setLessDamage(true);
    }

    @Test
    public void testCapacityProperties() {
        assertThat(capacity.getStateCapacity()).isEqualTo(StateCapacity.MAGIC);
        assertThat(capacity.getLessDamage()).isTrue();
    }

    @Test
    public void testCapacityConstraints() {
        capacity.setStateCapacity(null);
        assertThat(validator.validate(capacity)).isNotEmpty();

        capacity.setStateCapacity(StateCapacity.MAGIC);
        capacity.setLessDamage(null);
        assertThat(validator.validate(capacity)).isNotEmpty();
    }

    @Test
    public void testCapacityLifecycle() {
        capacityRepository.save(capacity);
        assertThat(capacity.getId()).isNotNull();

        capacity.setLessDamage(false);
        capacityRepository.save(capacity);
        assertThat(capacity.getLessDamage()).isFalse();

        capacityRepository.delete(capacity);
        assertThat(capacityRepository.findById(capacity.getId())).isEmpty();
    }

    @Test
    public void testCapacityQueries() {
        capacityRepository.save(capacity);
        assertThat(capacityRepository.findAll().size()).isEqualTo(9);

        assertThat(capacityRepository.findByStateCapacity(StateCapacity.MAGIC).size()).isEqualTo(3);
        assertThat(capacityRepository.findByStateCapacity(StateCapacity.MELEE).size()).isEqualTo(2);
    }
}
