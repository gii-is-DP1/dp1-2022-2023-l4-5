package org.springframework.samples.nt4h.card.ability.wizardEffects;

import org.springframework.samples.nt4h.action.DropCardFromHand;
import org.springframework.samples.nt4h.action.HealWound;
import org.springframework.samples.nt4h.action.RecoverCard;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class OrbeCurativo {
    public void execute(Player player) {
        new HealWound(player).executeAction();
        Game game = player.getGame();
        game.getPlayers().forEach(x -> IntStream.range(0, 2).forEach(i -> new RecoverCard(x).executeAction()));
        new DropCardFromHand(player, AbilityCardType.WIZARD).executeAction();
    }
}
