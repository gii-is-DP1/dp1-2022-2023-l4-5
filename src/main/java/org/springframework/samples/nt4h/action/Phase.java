package org.springframework.samples.nt4h.action;

public enum Phase {
    START,
    EVADE,
    HERO_ATTACK,
    ENEMY_ATTACK,
    MARKET,
    REESTABLISHMENT;

    @Override
    public String toString() {
        if (this == START) return "Start";
        if (this == EVADE) return "evasion";
        else if (this == HERO_ATTACK) return "heroAttack";
        else if (this == ENEMY_ATTACK) return "enemyAttack";
        else if (this == MARKET) return "market";
        else if (this == REESTABLISHMENT) return "reestablishment";
        else return "Unknown";
    }
}
