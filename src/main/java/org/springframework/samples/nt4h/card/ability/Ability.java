package org.springframework.samples.nt4h.card.ability;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.nt4h.card.Card;
import org.springframework.samples.nt4h.card.hero.Role;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

    @Min(1)
    private Integer quantity;

    private AbilityEffectEnum abilityEffectEnum;
}
