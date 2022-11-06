package org.springframework.samples.petclinic.player;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.ability.AbilityInGame;
import org.springframework.samples.petclinic.card.hero.Hero;
import org.springframework.samples.petclinic.game_manager.GameManager;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "players")
public class Player extends NamedEntity {

    private Integer gold;
    private Integer glory;
    private Boolean evasion;
    private Integer numOrcsKilled;
    private Integer numWarLordKilled;
    private Integer sequence;

    // Reducir número de relaciones.

    //Relaciones

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Hero> heroes;

    // Se crean al crear al jugador.
    @OneToMany
    private List<AbilityInGame> inHand;

    @OneToMany
    private List<AbilityInGame> inDeck;

    @OneToMany
    private List<AbilityInGame> inDiscard;

    @ManyToOne(cascade = CascadeType.ALL)
    private GameManager gameManagers;
}
