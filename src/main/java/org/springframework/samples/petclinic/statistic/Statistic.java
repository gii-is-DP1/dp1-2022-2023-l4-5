package org.springframework.samples.petclinic.statistic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

// TODO: darle un prop?sito a esta clase.
@Getter
@Setter
@Entity
public class Statistic extends BaseEntity {

    // TODO: decidir si hace fañta decidir un mínimo.
    @Column(columnDefinition = "int default 0")
    private Integer allGold;

    @Column(columnDefinition = "int default 0")
    private Integer allGlory;

    @Column(columnDefinition = "int default 0")
    private Integer numOrcsKilled;

    @Column(columnDefinition = "int default 0")
    private Integer numWarLordKilled;

    @Column(columnDefinition = "int default 0")
    private Integer numWinnedGames;

    @Column(columnDefinition = "int default 0")
    private Integer numPlayedGames;

    @Column(columnDefinition = "int default 0")
    private Integer allDamgeDealed;

    @Column(columnDefinition = "int default 0")
    private Integer allDamageDealedToNightLords;

    @OneToOne(cascade = CascadeType.ALL)
    private User usuario;


}
