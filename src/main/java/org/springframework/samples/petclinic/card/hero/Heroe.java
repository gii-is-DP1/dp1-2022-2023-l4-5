package org.springframework.samples.petclinic.card.hero;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.capacity.Capacity;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.ability.Ability;
import org.springframework.samples.petclinic.card.enemy.Enemy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
public class Heroe extends Card {

    @NotNull
    private Integer health;

    @NotNull
    private Role role;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ability> deck;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ability> hand;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ability> discard;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Enemy> enemies;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Capacity> capacities;
}
