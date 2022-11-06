package org.springframework.samples.petclinic.effect;


import org.springframework.samples.petclinic.card.enemy.Enemy;
import org.springframework.samples.petclinic.card.hero.Hero;
import org.springframework.samples.petclinic.player.Player;

public abstract class Effect {

    private Phase phase;

    boolean useEffect() {
        return false;
    }

    boolean useEffect(Player player) {
        return false;
    }

    boolean UseEffect(Enemy enemy) {
        return false;
    }

    boolean useEffect(Player player, Enemy enemy) {
        return false;
    }

    boolean useEffect(Hero... heros) {
        return false;
    }
}
