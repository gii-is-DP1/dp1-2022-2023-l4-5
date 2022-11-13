package org.springframework.samples.nt4h.effect;

import org.springframework.samples.nt4h.card.hero.HeroInGame;

public class CureHero extends Effect {

    public static Integer life = 1;
    public Phase phase = Phase.HERO_ATTACK;

    @Override
    public boolean useEffect(HeroInGame heroesInGame) {
        var hero = heroesInGame.getHero();
        if (heroesInGame.getActualHealth() < hero.getHealth()) {
            heroesInGame.setActualHealth(heroesInGame.getActualHealth() + life);
            return true;
        }

        return false;
    }
}
