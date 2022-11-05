package org.springframework.samples.petclinic.card.enemy;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.hero.Heroe;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Enemy extends Card {

    @NotNull
    @Max(value = 10)
    private Integer health;

    @NotNull
    @Range(min = 2, max = 10)
    private Integer attack;

    //Relaciones
    @ManyToOne(cascade = CascadeType.ALL)
    private Heroe heroes;
}
