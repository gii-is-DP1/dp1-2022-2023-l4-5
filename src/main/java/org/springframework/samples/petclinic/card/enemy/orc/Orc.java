package org.springframework.samples.petclinic.card.enemy.orc;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.enemy.Enemy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Orc extends Enemy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Range(min = 1, max = 4)
    private Integer glory;

    @NotNull
    @Range(min = 1, max = 3)
    private Integer gold;

    @NotNull
    private Boolean hasCure, lessDamageWizard;
}
