package org.springframework.samples.nt4h.turn.exceptions;

public class TooManyAbilitiesException extends Exception {
    public TooManyAbilitiesException() {
        super("You have to discard more abilities.");
    }
}
