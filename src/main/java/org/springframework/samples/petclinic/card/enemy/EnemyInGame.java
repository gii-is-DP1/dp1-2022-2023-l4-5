package org.springframework.samples.petclinic.card.enemy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.enemy.night_lord.NightLord;
import org.springframework.samples.petclinic.card.enemy.orc.Orc;
import org.springframework.samples.petclinic.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "enemies_in_game")
public class EnemyInGame extends BaseEntity {
    @NotNull
    @Max(value = 10)
    private Integer actualHealth;

    @NotNull
    private boolean isNightLord;

    @ManyToOne
    private Orc orc;

    @ManyToOne
    private NightLord nightLord;
}
