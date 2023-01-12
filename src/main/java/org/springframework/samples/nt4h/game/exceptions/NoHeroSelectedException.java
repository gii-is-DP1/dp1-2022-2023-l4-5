package org.springframework.samples.nt4h.game.exceptions;

public class NoHeroSelectedException extends Exception {
    public NoHeroSelectedException() {
        super("No hero selected");
    }
}
