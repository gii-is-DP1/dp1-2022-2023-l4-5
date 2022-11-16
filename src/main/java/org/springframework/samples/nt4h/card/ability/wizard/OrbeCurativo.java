package org.springframework.samples.nt4h.card.ability.wizard;

import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class OrbeCurativo {
    public void execute(Player player) {
        new HealWounds(player).execute();
        Game game = player.getGame();
        game.getPlayers().forEach(x -> IntStream.range(0, 2).forEach(i -> new RecoverCard(x).execute()));
        new DropCardFromHand(player, AbilityEffectEnum.ORBE_CURATIVO).execute();
    }
}
