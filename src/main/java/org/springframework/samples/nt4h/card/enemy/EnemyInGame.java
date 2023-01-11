package org.springframework.samples.nt4h.card.enemy;

import lombok.*;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EnemyInGame extends BaseEntity {
    @NotNull
    @Max(value = 10)
    private Integer actualHealth;

    @NotNull
    private boolean isNightLord;

    @ManyToOne
    private Enemy enemy;

    public static EnemyInGame createEnemy(Boolean isNightLord, Enemy enemy) {
        return EnemyInGame.builder().enemy(enemy).actualHealth(enemy.getHealth()).isNightLord(isNightLord).build();
    }

    public void onDeleteSetNull() {
        enemy = null;
    }

}
