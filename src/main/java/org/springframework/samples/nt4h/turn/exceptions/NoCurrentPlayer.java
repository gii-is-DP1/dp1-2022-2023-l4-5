package org.springframework.samples.nt4h.turn.exceptions;

public class NoCurrentPlayer extends Exception {

    public NoCurrentPlayer() {
        super("No current player");
    }

}

