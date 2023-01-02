package org.springframework.samples.nt4h.statistic;

import lombok.*;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// TODO: darle un prop?sito a esta clase.
@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Statistic extends BaseEntity {

    @NotNull
    @Min(0)
    private Integer gold;

    @NotNull
    @Min(0)
    private Integer glory;

    @NotNull
    @Min(0)
    private Integer numOrcsKilled;

    @NotNull
    @Min(0)
    private Integer numWarLordKilled;

    @NotNull
    @Min(0)
    private Integer numWonGames;

    @NotNull
    @Min(0)
    private Integer numPlayedGames;

    @NotNull
    @Min(0)
    private Integer damageDealt;

    @NotNull
    @Min(0)
    private Integer damageDealtToNightLords;

    public static Statistic createStatistic() {
        return Statistic.builder().glory(0).gold(0).numWarLordKilled(0).numOrcsKilled(0)
            .damageDealt(0).damageDealtToNightLords(0).numPlayedGames(0).numWonGames(0).build();
    }
}
