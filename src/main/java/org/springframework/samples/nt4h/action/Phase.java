package org.springframework.samples.nt4h.action;

public enum Phase {
    EVADE,
    HERO_ATTACK,
    ENEMY_ATTACK,
    MARKET,
    RESUPPLY;

    public Phase nextPhase() {
        return values()[(ordinal() + 1) % values().length];
    }
}
