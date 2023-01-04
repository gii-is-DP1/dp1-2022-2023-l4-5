package org.springframework.samples.nt4h.action;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.turn.TurnService;

@AllArgsConstructor
public class EndAttackPhase implements Action {


    @Autowired
    TurnService turnService;
    Integer turnId;

    @Override
    public void executeAction() {
        Turn turn = turnService.getTurnByID(turnId);
        turn.setPhase(Phase.ENEMY_ATTACK);
    }

}
