package org.springframework.samples.petclinic.card.hero;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.capacity.Capacity;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.ability.Ability;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "heroes")
public class  Hero extends Card {

    @NotNull
    private Integer health;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    private List<Ability> abilities;

    @ManyToMany
    private List<Capacity> capacities;
}
