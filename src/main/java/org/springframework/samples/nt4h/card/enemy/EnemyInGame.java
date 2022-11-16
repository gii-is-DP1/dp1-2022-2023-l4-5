package org.springframework.samples.nt4h.card.enemy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.night_lord.NightLord;
import org.springframework.samples.nt4h.card.enemy.orc.Orc;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
public class EnemyInGame extends BaseEntity {
    @NotNull
    @Max(value = 10)
    private Integer actualHealth;

    private boolean isDead;

    // falta relacion
    private List<AbilityEffectEnum> playedCardsOnMeInTurn;
}
