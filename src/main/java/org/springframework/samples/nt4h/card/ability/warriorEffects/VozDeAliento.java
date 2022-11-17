package org.springframework.samples.nt4h.card.ability.warriorEffects;

import org.springframework.samples.nt4h.action.DrawCards;
import org.springframework.samples.nt4h.action.GainGlory;
import org.springframework.samples.nt4h.action.RecoverCard;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class VozDeAliento {
    public void execute(Player player) {
        Game game = player.getGame();
        new DrawCards(1, player).executeAction();
        new GainGlory(1, player).executeAction();
        game.getPlayers().forEach(x -> IntStream.range(0, 2).forEach(i -> new RecoverCard(x).executeAction()));
    }
}
