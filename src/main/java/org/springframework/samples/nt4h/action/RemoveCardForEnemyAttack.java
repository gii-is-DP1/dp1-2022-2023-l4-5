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
    private Game game;
    private List<EnemyInGame> getEnemiesInBattle;


    @Override
    public void executeAction() {
        Integer damage = 0;
        if (getEnemiesInBattle.size() != 0) {  // Si hay enemigos en el campo si no pues no recibe daño el heroe
            for (int i = 0; i <= getEnemiesInBattle.size(); i++) {  //calculamos el daño total inflijido
                damage = damage + getEnemiesInBattle.get(i).getActualHealth();
            }

            for (int j = 0; j <= damage; j++) {  //retiramos las cartas segun el daño recibido
                player.getInDeck().remove(j);
                player.getInDiscard().add(player.getInDeck().remove(j));
            }
        }
    }
}

