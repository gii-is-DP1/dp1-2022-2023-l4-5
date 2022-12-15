package org.springframework.samples.nt4h.turn;


import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.InflictWounds;
import org.springframework.samples.nt4h.action.RemoveCardForEnemyAttack;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AttackEnemyService {

    private final PlayerService playerService;

    //Obetener el daño total de los enemigos en batalla

    public Integer attackEnemyToActualPlayer(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        if (game.getActualOrcs().isEmpty()) return 0;
        // Si hay enemigos en el campo si no pues no recibe daño el heroe
        int damage = game.getActualOrcs().stream().mapToInt(EnemyInGame::getActualHealth).sum();
        if (currentPlayer.getInDeck().size() <= damage) { //si el daño es mayor o igual a la cantidad de cartass quue tengo pues recibo la herida
            new InflictWounds(currentPlayer).executeAction();  //Recibe herida
            if (Objects.equals(currentPlayer.getWounds(), currentPlayer.getHealth())) {
                game.getPlayers().remove(currentPlayer);  // de momento sales de la partida, mas adelante cambia a vista espectador
                playerService.getOutGame(currentPlayer, game);
            }
        } else {
            new RemoveCardForEnemyAttack(currentPlayer, damage).executeAction(); //no recibe herida
        }
        return damage;
    }
}
