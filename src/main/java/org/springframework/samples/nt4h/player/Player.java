package org.springframework.samples.nt4h.player;

import com.google.common.collect.Lists;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.model.NamedEntity;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.user.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "players")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Player extends NamedEntity {

    @Min(0)
    private Integer gold;

    @Min(0)
    private Integer glory;

    private Boolean hasEvasion;

    @Min(0)
    private Integer numOrcsKilled;

    @Min(0)
    private Integer numWarLordKilled;


    @Min(0)
    private Integer damageDealed;

    @Min(0)
    private Integer damageDealedToNightLords;

    @Range(min = 1, max = 4)
    private Integer sequence;


    private Boolean ready;

    private Boolean host;

    private Integer wounds;

    private Integer damageProtect;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;


    //Relaciones
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    @Getter(AccessLevel.NONE)
    private List<HeroInGame> heroes;
    // Se crean al crear al jugador.
    @OneToMany(cascade = CascadeType.ALL)
    @Getter(AccessLevel.NONE)
    private List<Turn> turn;


    public List<HeroInGame> getHeroes() {
        if (heroes == null)
            heroes = Lists.newArrayList();
        return heroes;
    }

    public List<Turn> getTurn() {
        if (turn == null)
            turn = Lists.newArrayList();
        return turn;
    }


    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;
    // Cartas
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    @Getter(AccessLevel.NONE)
    private List<AbilityInGame> inHand;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    @Getter(AccessLevel.NONE)
    private List<AbilityInGame> inDeck;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    @Getter(AccessLevel.NONE)
    private List<AbilityInGame> inDiscard;

    public void addHero(HeroInGame hero) throws RoleAlreadyChosenException {
        if (heroes == null) heroes = Lists.newArrayList();
        else if (hasRoleAlreadyBeenChosen(hero.getHero().getRole()))
            throw new RoleAlreadyChosenException();
        else heroes.add(hero);
    }

    public boolean hasRoleAlreadyBeenChosen(Role role) {
        return heroes.stream().anyMatch(hero -> hero.getHero().getRole().equals(role));
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

    public List<AbilityInGame> shuffleDeck() {
        Collections.shuffle(inDeck);
        return inDeck;
    }

    public void addAbilityInHand(AbilityInGame ability) {
        if (inHand == null) {
            inHand = Lists.newArrayList(ability);
        } else {
            inHand.add(ability);
        }
    }

    public void addAbilityInDeck(AbilityInGame ability) {
        if (inDeck == null) {
            inDeck = Lists.newArrayList(ability);
        } else {
            inDeck.add(ability);
        }
    }

    public void addAbilityInDiscard(AbilityInGame ability) {
        if (inDiscard == null) {
            inDiscard = Lists.newArrayList(ability);
        } else {
            inDiscard.add(ability);
        }
    }

    public void removeAbilityInHand(AbilityInGame ability) {
        if (inHand == null) {
            inHand = Lists.newArrayList();
        } else {
            inHand.remove(ability);
        }
    }

    public void removeAbilityInDeck(AbilityInGame ability) {
        if (inDeck == null) {
            inDeck = Lists.newArrayList();
        } else {
            inDeck.remove(ability);
        }
    }

    public void removeAbilityInDiscard(AbilityInGame ability) {
        if (inDiscard == null) {
            inDiscard = Lists.newArrayList();
        } else {
            inDiscard.remove(ability);
        }
    }

    public Boolean userHasSameNameAsPlayer(User user) {
        return getName().equals(user.getUsername());
    }

}
