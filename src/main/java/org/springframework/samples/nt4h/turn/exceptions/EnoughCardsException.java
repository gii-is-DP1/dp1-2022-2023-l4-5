package org.springframework.samples.nt4h.turn.exceptions;

public class EnoughCardsException extends Exception {
    public EnoughCardsException() {
        super("There are enough cards.");
    }
}
