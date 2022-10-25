package org.springframework.samples.petclinic.gameManager;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.enumer.Accesibility;
import org.springframework.samples.petclinic.enumer.NumHeroes;
import org.springframework.samples.petclinic.enumer.Phase;
import org.springframework.samples.petclinic.model.BaseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
public class GameManager extends BaseEntity {

    @NotNull
    private Date startDate;

    @NotNull
    private Date finishDate;

    @NotNull
    @Range(min = 1, max = 4)
    private Integer actualPlayer;

    @NotNull
    @Range(min = 1, max = 2)
    private Integer mode;

    private Phase phase;

    private NumHeroes numHeroes;

    @NotEmpty
    private String password;

    private Accesibility accessibility;

    //faltan las relaciones
}
