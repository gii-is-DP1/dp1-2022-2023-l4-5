package org.springframework.samples.nt4h.card.product.marketEffect;

import org.springframework.samples.nt4h.action.DropCardFromHand;
import org.springframework.samples.nt4h.action.HealWound;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class PocionCurativa {
    public void execute(Player player) {
        new HealWound(player).executeAction();
        new DropCardFromHand(player, AbilityCardType.MARKET).executeAction();
    }
}
