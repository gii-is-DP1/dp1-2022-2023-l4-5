package org.springframework.samples.nt4h.card.enemy;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.nt4h.card.Card;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

// Comprobar si los getter y setters funcionan.
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Enemy extends Card {
    @NotNull
    @Range(min = 2, max = 10)
    private Integer health;
}
