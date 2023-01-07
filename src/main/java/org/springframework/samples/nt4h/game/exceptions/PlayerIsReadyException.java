package org.springframework.samples.nt4h.game.exceptions;

public class PlayerIsReadyException extends Exception {

        public PlayerIsReadyException() {
            super("Player is ready.");
        }

}
