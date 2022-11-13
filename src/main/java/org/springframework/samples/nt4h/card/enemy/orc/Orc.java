package org.springframework.samples.nt4h.card.enemy.orc;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.nt4h.card.enemy.Enemy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "orcs")
public class Orc extends Enemy {

    @NotNull
    @Range(min = 1, max = 4)
    private Integer glory;

    @NotNull
    @Range(min = 0, max = 2)
    private Integer gold;

    @NotNull
    private Boolean hasCure;

    @NotNull
    private Boolean lessDamageWizard;
}
