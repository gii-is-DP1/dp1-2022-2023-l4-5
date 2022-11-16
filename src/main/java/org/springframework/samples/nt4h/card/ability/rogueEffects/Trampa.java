package org.springframework.samples.nt4h.card.ability.rogueEffects;

import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class Trampa {
    public void execute(Player player, EnemyInGame enemy){
        enemy.getPermanentEffectCardsUsed().add(AbilityEffectEnum.TRAMPA);
        new ReceiveDamage(enemy, player).execute();
        new Attack(enemy.getActualHealth(), player, enemy).execute();
    }
}
