package org.springframework.samples.nt4h.turn;

public class EnoughEnemiesException extends Exception {
    public EnoughEnemiesException() { super("No faltan enemigos."); }
}
