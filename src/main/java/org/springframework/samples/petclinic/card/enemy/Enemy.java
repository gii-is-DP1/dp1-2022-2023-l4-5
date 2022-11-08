package org.springframework.samples.petclinic.card.enemy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

// Comprobar si los getter y setters funcionan.
@MappedSuperclass
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Enemy extends Card {
    @NotNull
    @Max(value = 10)
    private Integer health;
}
