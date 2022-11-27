package org.springframework.samples.nt4h.card.ability.wizardEffects;

import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class GolpeDeBaston {
    public void execute(Player player, EnemyInGame enemy) {

        int damage = 1;

        if (enemy.getPermanentEffectCardsUsed().contains(AbilityEffectEnum.GOLPE_DE_BASTON))
            damage = 2;

        new Attack(damage, player, enemy).executeAction();

        player.getGame().getActualOrcs()
            .forEach(x -> x.getPermanentEffectCardsUsed().add(AbilityEffectEnum.GOLPE_DE_BASTON));
    }
}
