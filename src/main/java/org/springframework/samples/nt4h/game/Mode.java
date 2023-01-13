package org.springframework.samples.nt4h.game;

public enum Mode {
    UNI_CLASS(1), MULTI_CLASS(2);

    private final int numHeroes;

    Mode(int numHeroes) {
        this.numHeroes = numHeroes;
    }

    public int getNumHeroes() {
        return numHeroes;
    }
}
