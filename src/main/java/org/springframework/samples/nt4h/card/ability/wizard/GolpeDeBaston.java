package org.springframework.samples.nt4h.card.ability.wizard;

import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class GolpeDeBaston {
    public void execute(Player player, EnemyInGame enemy) {

        Integer damage = 1;

        if (enemy.getPermanentEffectCardsUsed().contains(AbilityEffectEnum.GOLPE_DE_BASTON))
            damage = 2;

        new Attack(damage, player, enemy).execute();

        player.getGame().getOrcs()
            .forEach(x -> x.getPermanentEffectCardsUsed().add(AbilityEffectEnum.GOLPE_DE_BASTON));
    }
}
