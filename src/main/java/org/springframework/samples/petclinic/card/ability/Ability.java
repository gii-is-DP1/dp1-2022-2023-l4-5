package org.springframework.samples.petclinic.card.ability;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.enumm.Role;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Ability extends Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private Role role;

    @NotNull
    @Range(min = 0, max = 4)
    private Integer attack;
}
