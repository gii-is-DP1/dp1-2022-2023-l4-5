package org.springframework.samples.nt4h.turn;

import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Turn extends BaseEntity {

    @Enumerated
    private Phase phase;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<AbilityInGame> usedAbilities;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<EnemyInGame> usedEnemies;

    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;

    public void addEnemy(EnemyInGame attackedEnemy) {
        if(usedEnemies == null) {
            usedEnemies = Lists.newArrayList(attackedEnemy);
        } else {
            usedEnemies.add(attackedEnemy);
        }
    }

    public void addAbility(AbilityInGame usedAbility) {
        if(usedAbilities == null) {
            usedAbilities = Lists.newArrayList(usedAbility);
        } else {
            usedAbilities.add(usedAbility);
        }
    }

}

