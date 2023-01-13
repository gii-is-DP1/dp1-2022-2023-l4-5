package org.springframework.samples.nt4h.turn;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TurnServiceTest {
    @Autowired
    protected TurnService turnService;

    @MockBean
    private Advise advise;

    @BeforeAll
    void ini() {
        Turn turn = new Turn();
        turn.setPhase(Phase.EVADE);
        turnService.saveTurn(turn);
    }
    @Test
    public void shouldFindAll(){
        List<Turn> ls = turnService.getAllTurns();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(1,ls.size());
    }
}

