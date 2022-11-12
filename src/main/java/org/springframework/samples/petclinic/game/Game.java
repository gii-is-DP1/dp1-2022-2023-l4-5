package org.springframework.samples.petclinic.game;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.card.enemy.EnemyInGame;
import org.springframework.samples.petclinic.effect.Phase;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.turn.Turn;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends NamedEntity {

    //TODO: DESCOMENAR CUANDO FUFE EL JSP COMPLETO

    //@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime startDate;


    //@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime finishDate;

    @NotNull
    @Range(min = 1, max = 4)
    private Integer maxPlayers;



    @NotNull
    @Enumerated(EnumType.STRING)
    private Mode mode;


    @Enumerated(EnumType.STRING)
    private Phase phase;


    private String password;


    @Enumerated(EnumType.STRING)
    private Accessibility accessibility;

    //@NotNull
    private boolean hasStages;


    @OneToMany
    private List<Turn> turn;

    @OneToMany
    @Size(max = 3)
    private List<EnemyInGame> orcs;

    //@NotNull
    @OneToMany
    private List<EnemyInGame> passiveOrcs;

    @OneToMany
    private List<Player> players;
}
