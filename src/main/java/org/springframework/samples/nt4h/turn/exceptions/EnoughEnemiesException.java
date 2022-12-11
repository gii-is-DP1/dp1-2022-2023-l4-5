package org.springframework.samples.nt4h.turn.exceptions;

public class EnoughEnemiesException extends Exception {
    public EnoughEnemiesException() { super("No faltan enemigos."); }
}
