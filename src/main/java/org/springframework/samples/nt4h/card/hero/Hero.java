package org.springframework.samples.nt4h.card.hero;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.card.Card;
import org.springframework.samples.nt4h.card.ability.Ability;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "heroes")
public class  Hero extends Card {

    //@NotNull
    //@Range(min = 2, max = 3)
    private Integer health;

    //@NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    //@NotNull
    @ManyToMany
    private List<Ability> abilities;

    //@NotNull
    @ManyToMany
    private List<Capacity> capacities;

}