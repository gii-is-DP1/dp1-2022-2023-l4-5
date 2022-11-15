package org.springframework.samples.nt4h.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.model.NamedEntity;
import org.springframework.samples.nt4h.turn.Turn;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "players")
// @ToString(of = {"name"})
public class Player extends NamedEntity {

    @Min(0)

    private Integer gold;


    @Min(0)

    private Integer glory;

    private Boolean hasEvasion;

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
    private Integer damageDealedToNightLords;

    // @NotNull
    @Range(min = 1, max = 4)
    private Integer sequence;  // Para elegir a quien le toca.


    private Boolean ready;

    private Boolean host;

    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthDate;

    //Relaciones
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    @Getter(AccessLevel.NONE)
    private Set<HeroInGame> heroes;
    // Se crean al crear al jugador.
    @OneToMany
    @Getter(AccessLevel.NONE)
    private List<Turn> turn;
    @OneToMany
    @Getter(AccessLevel.NONE)
    private List<AbilityInGame> inHand;
    @OneToMany
    @Getter(AccessLevel.NONE)
    private List<AbilityInGame> inDeck;
    @OneToMany
    @Getter(AccessLevel.NONE)
    private List<AbilityInGame> inDiscard;

    public Set<HeroInGame> getHeroes() {
        if (heroes == null) {
            heroes = Sets.newHashSet();
        }
        return heroes;
    }

    public List<Turn> getTurn() {
        if (turn == null) {
            turn = Lists.newArrayList();
        }
        return turn;
    }

    public List<AbilityInGame> getInHand() {
        if (inHand == null) {
            inHand = Lists.newArrayList();
        }
        return inHand;
    }

    public List<AbilityInGame> getInDeck() {
        if (inDeck == null) {
            inDeck = Lists.newArrayList();
        }
        return inDeck;
    }

    public List<AbilityInGame> getInDiscard() {
        if (inDiscard == null) {
            inDiscard = Lists.newArrayList();
        }
        return inDiscard;
    }

    @ManyToOne
    private Game game;

    public void addHero(HeroInGame hero) {
        if (heroes == null) {
            heroes = Sets.newHashSet(hero);
        } else {
            heroes.add(hero);
        }
    }

}
