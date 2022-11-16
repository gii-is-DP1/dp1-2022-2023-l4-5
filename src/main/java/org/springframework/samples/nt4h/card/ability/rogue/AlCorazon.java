package org.springframework.samples.nt4h.card.ability.rogue;

import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class AlCorazon {
    public void execute(Player player, EnemyInGame enemy) {
        new Attack(4, player, enemy).execute();

        if(!enemy.getPermanentEffectCardsUsed().contains(AbilityEffectEnum.AL_CORAZON)){
            new GoldOnKill(1, enemy, player).execute();
        }

        player.getGame().getOrcs()
            .forEach(x -> x.getPermanentEffectCardsUsed().add(AbilityEffectEnum.AL_CORAZON));

        new DiscardCards(1, player).execute();
    }
}
