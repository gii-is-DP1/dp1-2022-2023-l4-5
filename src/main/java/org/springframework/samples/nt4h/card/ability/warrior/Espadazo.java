package org.springframework.samples.nt4h.card.ability.warrior;

import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class Espadazo {
    public void execute(Player player, EnemyInGame enemy) {
        new Attack(1, player, enemy).execute();

        if (!enemy.getPermanentEffectCardsUsed().contains(AbilityEffectEnum.ESPADAZO)) {
            new DrawCards(1, player).execute();
        }

        player.getGame().getOrcs()
            .forEach(x -> x.getPermanentEffectCardsUsed().add(AbilityEffectEnum.ESPADAZO));

    }
}
