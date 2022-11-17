package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.Turn;

import java.util.List;

@AllArgsConstructor
public class ChangeEnemy implements Action {

    Integer turnId;
    private EnemyInGame targetedEnemy;
    private Player actualPlayer;

    @Override
    public void executeAction() {
        Game game = actualPlayer.getGame();
        Turn turn = actualPlayer.getTurn().get(turnId);
        List<EnemyInGame> allOrcs = game.getAllOrcsInGame();
        if (allOrcs.size() >= 2) {
            List<EnemyInGame> actualOrcs = game.getActualOrcs();
            EnemyInGame lastEnemy = allOrcs.get(allOrcs.size() - 2);
            actualOrcs.remove(targetedEnemy);
            actualOrcs.add(lastEnemy);
            allOrcs.remove(lastEnemy);
            allOrcs.add(targetedEnemy);
            game.setActualOrcs(actualOrcs);
            game.setAllOrcsInGame(allOrcs);
        }
    }

}
