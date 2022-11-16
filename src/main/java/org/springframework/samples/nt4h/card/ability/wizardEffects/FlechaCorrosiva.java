package org.springframework.samples.nt4h.card.ability.wizardEffects;

import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class FlechaCorrosiva {
    public void execute(Player player, EnemyInGame enemy) {
        new Attack(1, player, enemy).execute();
        new DiscardCards(1, player).execute();
        enemy.getPermanentEffectCardsUsed().add(AbilityEffectEnum.FLECHA_CORROSIVA);
    }
}
