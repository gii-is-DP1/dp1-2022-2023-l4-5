package org.springframework.samples.nt4h.game.exceptions;

public class UserInAGameException extends Exception {

    public UserInAGameException() {
        super("User is already in a game.");
    }

}
