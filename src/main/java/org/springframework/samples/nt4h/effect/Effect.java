package org.springframework.samples.nt4h.effect;


import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.player.Player;

public abstract class Effect {

    public Phase phase;

    boolean useEffect() {
        return false;
    }

    boolean useEffect(Player player) {
        return false;
    }

    boolean useEffect(Player player, EnemyInGame enemyInGame) {
        return false;
    }

    boolean useEffect(HeroInGame... herosInGame) {
        return false;
    }


    boolean useEffect(Player player, EnemyInGame... enemiesInGame) {
        return false;
    }

    boolean useEffect(Player player, EnemyInGame enemyInGame, AbilityInGame abilityInGame) {
        return false;
    }

    boolean useEffect(HeroInGame heroesInGame) {
        return false;
    }
}
