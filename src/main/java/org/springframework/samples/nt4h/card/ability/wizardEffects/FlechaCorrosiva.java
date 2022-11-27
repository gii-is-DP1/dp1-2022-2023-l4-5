package org.springframework.samples.nt4h.card.ability.wizardEffects;

import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.action.DiscardCards;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class FlechaCorrosiva {
    public void execute(Player player, EnemyInGame enemy) {
        new Attack(1, player, enemy).executeAction();
        new DiscardCards(1, player).executeAction();
        enemy.getPermanentEffectCardsUsed().add(AbilityEffectEnum.FLECHA_CORROSIVA);
    }
}
