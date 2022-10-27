package org.springframework.samples.petclinic.player;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.ability.Ability;
import org.springframework.samples.petclinic.card.hero.Heroe;
import org.springframework.samples.petclinic.gameManager.GameManager;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.statistic.Statistic;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name= "Player")
public class Player extends NamedEntity {

    //Propiedades
    private Integer gold;
    private Integer glory;
    private Boolean evasion;
    private Integer numOrcsKilled;
    private Integer numWarLordKilled;
    private Integer order;
    @Range(min = 1, max = 5 )
    private Integer position;

    //Relaciones
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Heroe> heroes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ability> abilities;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistic statistics;
}
