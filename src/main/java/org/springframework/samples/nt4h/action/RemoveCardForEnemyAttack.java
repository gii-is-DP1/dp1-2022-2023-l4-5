package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.user.UserService;

import java.util.List;

@AllArgsConstructor
public class RemoveCardForEnemyAttack implements Action {

    private Player player;
    private Integer damage;


    @Override
    public void executeAction() {

        for (int j = 0; j <= damage; j++) {  //retiramos las cartas segun el daño recibido
            player.getInDeck().remove(j);
            player.getInDiscard().add(player.getInDeck().remove(j));
        }
    }
}

