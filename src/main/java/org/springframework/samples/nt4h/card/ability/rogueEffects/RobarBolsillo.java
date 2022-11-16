package org.springframework.samples.nt4h.card.ability.rogueEffects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class RobarBolsillo {
    @Autowired
    private GameService gameService;
    public void execute(Player player) {
        Integer gameId = player.getGame().getId();
        gameService.getGameById(gameId).getPlayers().forEach(x -> new StealCoin(player, x).execute());
    }
}
