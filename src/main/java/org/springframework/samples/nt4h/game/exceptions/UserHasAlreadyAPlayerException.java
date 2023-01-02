package org.springframework.samples.nt4h.game.exceptions;

public class UserHasAlreadyAPlayerException extends Exception {
        public UserHasAlreadyAPlayerException() {
            super("User has already a player.");
        }
}
