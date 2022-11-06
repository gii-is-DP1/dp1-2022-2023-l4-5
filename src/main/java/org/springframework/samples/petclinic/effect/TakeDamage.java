package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.card.hero.Hero;

import java.util.Arrays;

public class TakeDamage extends Effect {

    private final Integer damage = 1;

    @Override
    public boolean useEffect(Hero... heros) {
        Arrays.stream(heros).forEach(hero -> hero.setHealth(hero.getHealth() - damage));
        return true;
    }
}
