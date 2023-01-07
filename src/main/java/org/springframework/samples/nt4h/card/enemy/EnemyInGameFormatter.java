package org.springframework.samples.nt4h.card.enemy;

import lombok.AllArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
@AllArgsConstructor
public class EnemyInGameFormatter implements Formatter<EnemyInGame> {

    private EnemyService enemyService;

    @Override
    public EnemyInGame parse(String s, Locale locale) throws ParseException {
        return enemyService.getEnemyInGameById(Integer.parseInt(s));
    }

    @Override
    public String print(EnemyInGame enemyInGame, Locale locale) {
        return enemyInGame.getEnemy().getId().toString();
    }
}
