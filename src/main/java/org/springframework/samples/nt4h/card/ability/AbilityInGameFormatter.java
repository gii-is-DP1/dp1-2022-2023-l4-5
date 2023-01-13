package org.springframework.samples.nt4h.card.ability;

import lombok.AllArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class AbilityInGameFormatter implements Formatter<AbilityInGame> {

    private AbilityService abilityService;
    @Override
    public AbilityInGame parse(String s, Locale locale) {
        return abilityService.getAbilityInGameById(Integer.parseInt(s));
    }

    @Override
    public String print(AbilityInGame abilityInGame, Locale locale) {
        return abilityInGame.getId().toString();
    }
}
