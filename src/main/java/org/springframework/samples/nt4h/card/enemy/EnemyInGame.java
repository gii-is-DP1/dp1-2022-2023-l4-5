package org.springframework.samples.nt4h.card.enemy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.night_lord.NightLord;
import org.springframework.samples.nt4h.card.enemy.orc.Orc;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "enemies_in_game")
public class EnemyInGame extends BaseEntity {
    @NotNull
    @Max(value = 10)
    private Integer actualHealth;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = AbilityEffectEnum.class)
    @CollectionTable(name = "permanent_effect_cards_used", joinColumns = @JoinColumn(name = "enemy_in_game_id"))
    @Column(name = "ability_effect_enum")
    private Set<AbilityEffectEnum> permanentEffectCardsUsed = new HashSet<>();

    @NotNull
    private boolean isNightLord;

    @ManyToOne
    private Orc orc;

    @ManyToOne
    private NightLord nightLord;
}
