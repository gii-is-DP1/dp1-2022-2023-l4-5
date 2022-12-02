package org.springframework.samples.nt4h.turn;

public class EnoughCardsException extends Exception {
    public EnoughCardsException() {
        super("No te faltan cartas.");
    }
}
