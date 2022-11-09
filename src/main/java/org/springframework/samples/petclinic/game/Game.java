package org.springframework.samples.petclinic.game;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.card.enemy.night_lord.NightLord;
import org.springframework.samples.petclinic.card.enemy.orc.Orc;
import org.springframework.samples.petclinic.effect.Phase;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.turn.Turn;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate finishDate;

    @NotNull
    @Range(min = 1, max = 4)
    private Integer actualPlayer;

    // TODO: Decidir si son obligatorios.
    @NotNull
    @Enumerated(EnumType.STRING)
    private Mode mode;

    // TODO: Decidir si son obligatorios.
    @Enumerated(EnumType.STRING)
    private Phase phase;

    @NotEmpty
    private String password;

    // TODO: Decidir si son obligatorios.
    @Enumerated(EnumType.STRING)
    private Accessibility accessibility;


    @OneToMany
    private List<Turn> turn;

    // TODO: decidir si son EnemiesInGame.
    @OneToMany
    @Size(max = 3)
    private List<Orc> orc;

    @OneToOne
    private NightLord nightLord;

    @OneToMany
    private List<Orc> passiveOrc;
}
