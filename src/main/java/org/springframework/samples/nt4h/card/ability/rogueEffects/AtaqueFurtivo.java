package org.springframework.samples.nt4h.card.ability.rogueEffects;

import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.action.GoldOnKill;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class AtaqueFurtivo {
    public void execute(Player player, EnemyInGame enemy) {
        new Attack(2, player, enemy).executeAction();

        if(!enemy.getPermanentEffectCardsUsed().contains(AbilityEffectEnum.ATAQUE_FURTIVO)){
            new GoldOnKill(1, enemy, player).executeAction();
        }

        player.getGame().getActualOrcs()
            .forEach(x -> x.getPermanentEffectCardsUsed().add(AbilityEffectEnum.ATAQUE_FURTIVO));
    }
}
