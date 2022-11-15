package org.springframework.samples.nt4h.game.exceptions;

public class PlayerInOtherGameException extends Exception {

    public PlayerInOtherGameException() {
        super("Player is already in another game");
    }
}
