package org.springframework.samples.petclinic.player;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.ability.AbilityInGame;
import org.springframework.samples.petclinic.card.hero.HeroInGame;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "players")
public class Player extends NamedEntity {

    @Column(columnDefinition = "default 0")
    private Integer gold;
    @Column(columnDefinition = "default 0")
    private Integer glory;
    @Column(columnDefinition = "default 1")
    private Boolean evasion;
    @Column(columnDefinition = "default 0")
    private Integer numOrcsKilled;
    @Column(columnDefinition = "default 0")
    private Integer numWarLordKilled;
    private Integer sequence;  // Para elegir a quien le toca.

    //Relaciones
    @OneToMany(cascade = CascadeType.ALL)
    private Set<HeroInGame> heroes;

    // Se crean al crear al jugador.
    @OneToMany
    private List<AbilityInGame> inHand;

    @OneToMany
    private List<AbilityInGame> inDeck;

    @OneToMany
    private List<AbilityInGame> inDiscard;

    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;
}
