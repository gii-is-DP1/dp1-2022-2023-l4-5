package org.springframework.samples.petclinic.turn;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.ability.AbilityInGame;
import org.springframework.samples.petclinic.card.enemy.EnemyInGame;
import org.springframework.samples.petclinic.effect.Phase;
import org.springframework.samples.petclinic.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Turn extends BaseEntity {
    @Column(columnDefinition = "int default 0")
    private Integer gold;
    @Column(columnDefinition = "int default 0")
    private Integer glory;
    private Boolean evasion;
    @Enumerated(EnumType.STRING)
    private Phase phase;


    @ManyToMany
    private List<AbilityInGame> usedAbilities;

    @ManyToMany
    private List<EnemyInGame> usedEnemies;
}
