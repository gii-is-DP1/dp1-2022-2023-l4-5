package org.springframework.samples.petclinic.player;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.ability.Ability;
import org.springframework.samples.petclinic.card.hero.Heroe;
import org.springframework.samples.petclinic.game_manager.GameManager;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "players")
public class Player extends NamedEntity {

    //Propiedades
    private Integer gold;
    private Integer glory;
    private Boolean evasion;
    private Integer numOrcsKilled;
    private Integer numWarLordKilled;
    private Integer order;

    // Reducir número de relaciones.

    //Relaciones
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Heroe> heroes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ability> abilities;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameManager> gameManagers;
}
