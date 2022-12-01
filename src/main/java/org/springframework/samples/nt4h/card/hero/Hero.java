package org.springframework.samples.nt4h.card.hero;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.card.Card;
import org.springframework.samples.nt4h.card.ability.Ability;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "heroes")
public class  Hero extends Card {

    private Integer health;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Ability> abilities;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Capacity> capacities;

}
