package org.springframework.samples.nt4h.capacity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CapacityTest {

    Capacity capacity;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeEach
    void setUp() {
        capacity = Capacity.builder().stateCapacity(StateCapacity.MAGIC).lessDamage(true).build();
    }

    @Test
    void fine() {
        assertEquals(StateCapacity.MAGIC, capacity.getStateCapacity());
        assertEquals(true, capacity.getLessDamage());
    }

    @Test
    void notFineStateCapacity() {
        // El estado no puede ser nulo.
        capacity.setStateCapacity(null);
        assertTrue(!validator.validate(capacity).isEmpty(), "State capacity must not be null.");
    }

    @Test
    void notFineLessDamage() {
        // El lessDamage no puede ser nulo.
        capacity.setLessDamage(null);
        assertTrue(!validator.validate(capacity).isEmpty(), "Less damage must not be null.");
    }
}

