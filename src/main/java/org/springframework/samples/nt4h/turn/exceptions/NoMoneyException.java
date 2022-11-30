package org.springframework.samples.nt4h.turn.exceptions;

public class NoMoneyException extends Exception {

    public NoMoneyException() {
        super("You don't have enough money");
    }
}
