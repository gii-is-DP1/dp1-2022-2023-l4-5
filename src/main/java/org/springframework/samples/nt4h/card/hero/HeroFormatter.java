package org.springframework.samples.nt4h.card.hero;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class HeroFormatter implements Formatter<Hero> {

    private HeroService heroService;

    @Override
    public Hero parse(String text, Locale locale) {
        return heroService.getHeroByName(text.split(" - ")[0].trim());
    }

    @Override
    public String print(Hero object, Locale locale) {
        return object.getName() + " - " + object.getHealth() + " - " + object.getRole();
    }
}
