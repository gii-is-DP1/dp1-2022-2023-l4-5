package org.springframework.samples.nt4h.card.ability.rogueEffects;

import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class BallestaPrecisa {
    public void execute(Player player, EnemyInGame enemy) {
        Integer damage = 2;

        if (enemy.getPermanentEffectCardsUsed().contains(AbilityEffectEnum.BALLESTA_PRECISA)){
            damage = 3;
        }

        new Attack(damage, player, enemy).execute();

        player.getGame().getActualOrcs()
            .forEach(x -> x.getPermanentEffectCardsUsed().add(AbilityEffectEnum.BALLESTA_PRECISA));
    }
}
