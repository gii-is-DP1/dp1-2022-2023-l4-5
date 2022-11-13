package org.springframework.samples.nt4h.player;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.model.NamedEntity;
import org.springframework.samples.nt4h.turn.Turn;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "players")
public class Player extends NamedEntity {

    // @NotNull
    @Min(0)
    // @Column(columnDefinition = "int default 0")
    private Integer gold;

    // @NotNull
    @Min(0)
    // @Column(columnDefinition = "int default 0")
    private Integer glory;

    // @NotNull
    // @Column(columnDefinition = "int default 1")
    private Boolean evasion;

    // @NotNull
    @Min(0)
    // @Column(columnDefinition = "int default 0")
    private Integer numOrcsKilled;

    // @NotNull
    @Min(0)
    // @Column(columnDefinition = "int default 0")
    private Integer numWarLordKilled; // TODO: Cambiar por night lord.

    // @NotNull
    @Min(0)
    // @Column(columnDefinition = "int default 0")
    private Integer damageDealed;

    // @NotNull
    @Min(0)
    // @Column(columnDefinition = "int default 0")
    private Integer damageDealedToNightLords;

    // @NotNull
    @Range(min = 1, max= 4)
    private Integer sequence;  // Para elegir a quien le toca.

    // @NotNull
    // @Column(columnDefinition = "boolean default false")
    private Boolean ready;

    //Relaciones
    @OneToMany(cascade = CascadeType.ALL)
    private Set<HeroInGame> heroes;


    // Se crean al crear al jugador.
    @OneToMany
    private List<Turn> turn;

    @OneToMany
    private List<AbilityInGame> inHand;

    @OneToMany
    private List<AbilityInGame> inDeck;

    @OneToMany
    private List<AbilityInGame> inDiscard;

    @ManyToOne
    private Game game;

}
