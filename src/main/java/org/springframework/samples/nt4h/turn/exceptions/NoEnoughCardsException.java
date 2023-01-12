package org.springframework.samples.nt4h.turn.exceptions;

public class NoEnoughCardsException extends Exception {
    public NoEnoughCardsException() {
        super("There are enough cards.");
    }
}
