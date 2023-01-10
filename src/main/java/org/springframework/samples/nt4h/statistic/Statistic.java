package org.springframework.samples.nt4h.statistic;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

// TODO: darle un prop?sito a esta clase.
@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Statistic extends BaseEntity {

    @Min(0)
    private Integer gold;

    @Min(0)
    private Integer glory;


    @Min(0)
    private Integer numOrcsKilled;


    @Min(0)
    private Integer numWarLordKilled;


    @Min(0)
    private Integer numWonGames;

    @Min(0)
    private Integer numPlayedGames;

    @Min(0)
    private Integer timePlayed;

    @Range(max = 4, min = 2)
    private Integer numPlayers;

    @Min(0)
    private Integer damageDealt;

    @Min(0)
    private Integer damageDealtToNightLords;

    @NotNull
    private TipoEnt tipoEnt;

    public static Statistic createStatistic() {
        return Statistic.builder().glory(0).gold(0).numWarLordKilled(0).numOrcsKilled(0)
            .damageDealt(0).damageDealtToNightLords(0).numPlayedGames(0).numWonGames(0).build();
    }
}
