package org.springframework.samples.nt4h.game.exceptions;

public class IncorrectPasswordException extends Exception {
        public IncorrectPasswordException() {
            super("Incorrect password.");
        }
}
