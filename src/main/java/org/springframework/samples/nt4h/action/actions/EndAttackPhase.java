package org.springframework.samples.nt4h.action.actions;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.Turn;

@AllArgsConstructor
public class EndAttackPhase implements Action {

    @Autowired
    GameService gameService;
    Phase phase;
    Integer turnId;
    private Player player;

    @Override
    public void executeAction() {
        Integer gameId = player.getGame().getId();
        Game game = gameService.getGameById(gameId);
        Turn turn = gameService.findTurnById(turnId);
        turn.setPhase(Phase.ENEMY_ATTACK);
    }

}
