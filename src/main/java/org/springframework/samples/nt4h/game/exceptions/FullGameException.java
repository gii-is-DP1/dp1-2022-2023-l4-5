package org.springframework.samples.nt4h.game.exceptions;

public class FullGameException extends Exception {

    public FullGameException() {
        super("Game is full");
    }
}
