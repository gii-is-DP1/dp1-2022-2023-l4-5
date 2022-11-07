package org.springframework.samples.petclinic.card.enemy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.Card;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "enemies")
public class Enemy extends Card {
    @NotNull
    @Max(value = 10)
    private Integer health;
}
