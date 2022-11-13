package org.springframework.samples.nt4h.effect;

import org.springframework.samples.nt4h.card.hero.HeroInGame;

import java.util.Arrays;

public class TakeDamage extends Effect {

    public final Integer damage = 1;

    @Override
    public boolean useEffect(HeroInGame... heroesInGames) {
        Arrays.stream(heroesInGames).forEach(hero -> hero.setActualHealth(hero.getActualHealth() - damage));
        return true;
    }
}
