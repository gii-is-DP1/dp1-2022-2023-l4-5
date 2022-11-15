package org.springframework.samples.nt4h.effect;


import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;

import java.util.List;

@Getter
@Setter
public abstract class Effect {

    // Cuando empieza y acaba.
    Phase openTurn;
    // Datos
    private List<AbilityInGame> abilities;
    private List<EnemyInGame> enemies;
    private List<HeroInGame> heroes;
    private Phase closeTurn;

    // Para empezar y terminar el efecto.
    abstract void openEffect();

    abstract void closeEffect();
}
