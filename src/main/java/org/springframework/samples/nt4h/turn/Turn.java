package org.springframework.samples.nt4h.turn;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
public class Turn extends BaseEntity {

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer gold;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer glory;

    @NotNull
    private Boolean evasion;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Phase phase;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<AbilityInGame> usedAbilities;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<EnemyInGame> usedEnemies;
}
