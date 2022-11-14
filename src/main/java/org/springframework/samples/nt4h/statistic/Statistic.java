package org.springframework.samples.nt4h.statistic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// TODO: darle un prop?sito a esta clase.
@Getter
@Setter
@Entity
public class Statistic extends BaseEntity {

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer allGold;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer allGlory;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer numOrcsKilled;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer numWarLordKilled;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer numWinnedGames;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer numPlayedGames;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer allDamgeDealed;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer allDamageDealedToNightLords;




}