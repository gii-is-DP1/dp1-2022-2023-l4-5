package org.springframework.samples.nt4h.action.actions;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.turn.TurnService;

@AllArgsConstructor
public class EndAttackPhase implements Action {

    @Autowired
    GameService gameService;
    @Autowired
    TurnService turnService;
    Phase phase;
    Integer turnId;
    private Player player;

    @Override
    public void executeAction() {
        Integer gameId = player.getGame().getId();
        Game game = gameService.getGameById(gameId);
        Turn turn = turnService.getTurnByID(turnId);
        turn.setPhase(Phase.ENEMY_ATTACK);
    }

}
