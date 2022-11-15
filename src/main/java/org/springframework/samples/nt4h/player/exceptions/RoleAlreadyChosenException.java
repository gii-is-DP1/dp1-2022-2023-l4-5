package org.springframework.samples.nt4h.player.exceptions;

public class RoleAlreadyChosenException extends Exception {

    public RoleAlreadyChosenException() {
        super("Role already chosen");
    }
}
