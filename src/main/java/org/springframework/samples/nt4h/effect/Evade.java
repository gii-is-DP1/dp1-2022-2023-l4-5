package org.springframework.samples.nt4h.effect;

import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;

public class Evade extends Effect{
    public static final Integer numCards = 2;
    public Phase phase = Phase.START;
    @Override
    public boolean useEffect(Player player, Game game) {
        for (int i = 0; i < numCards; i++) {
            var card = player.getInHand().get(0);
            player.getInHand().remove(card);
            player.getInDiscard().add(card);
        }
        game.setPhase(Phase.RESUPPLY);
        return true;
    }
}
