package org.springframework.samples.nt4h.action;


import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;

public interface Action {

    void executeAction() throws NoMoneyException;

}
