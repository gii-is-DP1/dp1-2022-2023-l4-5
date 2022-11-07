package org.springframework.samples.petclinic.card.ability;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.hero.Hero;
import org.springframework.samples.petclinic.card.hero.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "abilities")
public class Ability extends Card {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Range(min = 0, max = 4)
    private Integer attack;

    @ManyToMany
    private List<Hero> hero;
}
