package org.springframework.samples.nt4h.card.ability.warriorEffects;

import org.springframework.samples.nt4h.action.DrawCards;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class Espadazo {
    public void execute(Player player, EnemyInGame enemy) {
        //new Attack(1, player, enemy).execute();

        if (!enemy.getPermanentEffectCardsUsed().contains(AbilityEffectEnum.ESPADAZO)) {
            new DrawCards(1, player).executeAction();
        }

        player.getGame().getActualOrcs()
            .forEach(x -> x.getPermanentEffectCardsUsed().add(AbilityEffectEnum.ESPADAZO));

    }
}
