package org.springframework.samples.petclinic.card.enemy;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.hero.Heroe;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Enemy extends Card {

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
     */

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
