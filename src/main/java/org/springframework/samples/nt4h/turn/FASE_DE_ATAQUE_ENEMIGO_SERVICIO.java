package org.springframework.samples.nt4h.turn;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.InflictWounds;
import org.springframework.samples.nt4h.action.RemoveCardForEnemyAttack;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameController;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;

@Service
@AllArgsConstructor
public class FASE_DE_ATAQUE_ENEMIGO_SERVICIO {

    private final PlayerService playerService;

    //Obetener el daño total de los enemigos en batalla

    public void attackEnemyToActualPlayer(Game game) {
      //Lo que hace el método :)
        //Cambiar mazo de habilidad
        //Recibir herida o no


        Integer damage = 0; //repito el bucle porque no se me he encariñado de el
        if (game.getActualOrcs().size() != 0) {  // Si hay enemigos en el campo si no pues no recibe daño el heroe
            for (int i = 0; i <= game.getActualOrcs().size(); i++) {  //calculamos el daño total inflijido
                damage = damage + game.getActualOrcs().get(i).getActualHealth();
            }
            if (game.currentPlayer.getInDeck().size() <= damage) { //si el daño es mayor o igual a la cantidad de cartass quue tengo pues recibo la herida
                Action DamageWithWounds = new InflictWounds(game.currentPlayer);  //Recibe herida
                DamageWithWounds.executeAction();
                if(game.currentPlayer.getWounds()==game.currentPlayer.getHeroes().get(0).getActualHealth()){
                    game.getPlayers().remove(game.currentPlayer);  // de momento sales de la partida, mas adelante cambia a vista espectador
                    playerService.getOutGame(game.currentPlayer, game);
                }
            } else {
                Action DamageWithOUTWounds = new RemoveCardForEnemyAttack(game.currentPlayer, damage); //no recibe herida
                DamageWithOUTWounds.executeAction();
            }
        }
        playerService.savePlayer(game.currentPlayer);
    }
}
