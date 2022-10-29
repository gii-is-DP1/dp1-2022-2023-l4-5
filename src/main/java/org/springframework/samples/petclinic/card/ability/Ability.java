package org.springframework.samples.petclinic.card.ability;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.hero.Role;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "abilities")
public class Ability extends Card {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private Role role;

    @NotNull
    @Range(min = 0, max = 4)
    private Integer attack;

}
