package org.springframework.samples.petclinic.player;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.hero.Heroe;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.*;
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

    //Relaciones
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Heroe> heroe;
}
