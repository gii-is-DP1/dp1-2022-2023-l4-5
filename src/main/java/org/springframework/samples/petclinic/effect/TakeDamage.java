package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.card.hero.HeroInGame;

import java.util.Arrays;

public class TakeDamage extends Effect {

    public final Integer damage = 1;

    @Override
    public boolean useEffect(HeroInGame... heroesInGames) {
        Arrays.stream(heroesInGames).forEach(hero -> hero.setActualHealth(hero.getActualHealth() - damage));
        return true;
    }
}
