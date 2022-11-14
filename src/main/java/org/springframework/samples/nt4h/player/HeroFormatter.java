package org.springframework.samples.nt4h.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class HeroFormatter implements Formatter<Hero> {

    @Autowired
    private HeroService heroService;

    @Override
    public Hero parse(String text, Locale locale) {
        return heroService.getHeroByName(text);
    }

    @Override
    public String print(Hero object, Locale locale) {
        return object.getName();
    }
}
