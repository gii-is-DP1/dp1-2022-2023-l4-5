package org.springframework.samples.petclinic.card.enemy;

import org.hibernate.validator.constraints.Range;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Enemy {

    @NotNull
    @Max(value = 10)
    private Integer health;

    @NotNull
    @Range(min = 2, max = 10)
    private Integer attack;
}
