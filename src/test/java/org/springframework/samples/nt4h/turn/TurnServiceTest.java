package org.springframework.samples.nt4h.turn;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.effect.Phase;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TurnServiceTest {
    @Autowired
    protected TurnService turnService;

    @BeforeAll
    void ini() {
        Turn turn = new Turn();
        turn.setEvasion(true);
        turn.setGlory(0);
        turn.setPhase(Phase.START);
        turn.setGold(0);
        turnService.saveTurn(turn);
    }
    @Test
    public void findByIDTrue(){
        Turn turn = turnService.getTurnByID(1);
        assertNotNull(turn);
        assertEquals(Phase.START, turn.getPhase());
    }
    @Test
    public void findByIDFalse(){
        Turn turn = turnService.getTurnByID(1);
        assertNotNull(turn);
        assertNotEquals(Phase.RESUPPLY, turn.getPhase());
    }
    @Test
    public void shouldFindByPhase(){
        List<Turn> ls = turnService.getTurnsByPhase(Phase.START);
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(1,ls.size());
    }
    @Test
    public void shouldFindAll(){
        List<Turn> ls = turnService.getAllTurns();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(1,ls.size());
    }
    @Test
    public void shouldInsertTurn(){
        Turn turn = new Turn();
        turn.setEvasion(true);
        turn.setGlory(0);
        turn.setPhase(Phase.START);
        turn.setGold(0);
        turnService.saveTurn(turn);
        assertEquals(turn,turnService.getTurnByID(2));
    }
    @Test
    public void shouldUpdateTurn(){
        Turn turn = turnService.getTurnByID(1);
        Phase oldPhase = turn.getPhase();
        Phase newPhase = Phase.RESUPPLY;
        turn.setPhase(newPhase);
        turnService.saveTurn(turn);
        assertEquals(newPhase,turnService.getTurnByID(1).getPhase());
        assertNotEquals(oldPhase,turnService.getTurnByID(1).getPhase());
    }
    @Test
    public void deleteTurnTest(){
        turnService.deleteTurnById(1);
        assertFalse(turnService.turnExists(1));
    }


}
