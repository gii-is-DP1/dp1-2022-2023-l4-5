package org.springframework.samples.nt4h.card.ability;


import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.InflictWounds;
import org.springframework.samples.nt4h.action.RemoveCardForEnemyAttack;
import org.springframework.samples.nt4h.card.Card;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameController;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FASE_DE_ATAQUE_ENEMIGO_SERVICIO {

    private final UserService userService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameController gameController;
    private final AbilityService abilityService;
    private final EnemyService enemyService;
    private final Player player;
    private final Game game;


    public final String VIEW_REESTABLISHMENT = "turns/reestablishmentPhase"; //Hay que cambiarlo

    private String message;
    private String messageType;

    @Autowired
    public FASE_DE_ATAQUE_ENEMIGO_SERVICIO(UserService userService, PlayerService playerService, TurnService turnService, GameController gameController, AbilityService abilityService, EnemyService enemyService, Player player, Game game) {
        this.playerService = playerService;
        this.userService = userService;
        this.turnService = turnService;
        this.gameController = gameController;
        this.abilityService = abilityService;
        this.enemyService = enemyService;
        this.player = player;
        this.game = game;
    }

    //necesito:
    // la camtidad de cartasd el mazo de habilidad,
    // el daño de todos los orcos del campo,
    // las cartas restates se van al mazo de descarte (suponogo)
    // las heridas el heroe
    // la vida del heroe

    @ModelAttribute("enemiesInBattle")
    public List<EnemyInGame> getEnemiesInBattle() {
        return gameController.getGame().getActualOrcs();
    }


    //AUXILIARES

    //Obetener el daño total de los enemigos en batalla


    public List<AbilityInGame> nuevaListaDeHbilidad() {

        //me falta la lista  List<EnemyInGame> getEnemiesInBattle
        //no se como se llama a los actions tampoco se muy bien como usarlos asique....

        Integer damage = 0; //repito el bucle porque no se me he encariñado de el
        if (game.getActualOrcs().size() != 0) {  // Si hay enemigos en el campo si no pues no recibe daño el heroe
            for (int i = 0; i <= game.getActualOrcs().size(); i++) {  //calculamos el daño total inflijido
                damage = damage + game.getActualOrcs().get(i).getActualHealth();
            }
        if (player.getInDeck().size()<= damage){ //si el daño es mayor o igual a la cantidad de cartass quue tengo pues recibo la herida
            Action DamageWithWounds = new InflictWounds(player);  //Recibe herida
            DamageWithWounds.executeAction();
        }else{
            Action DamageWithOUTWounds = new RemoveCardForEnemyAttack(player, game, game.getActualOrcs()); //no recibe herida
            DamageWithOUTWounds.executeAction();
        }
            return player.getInDeck(); //devuelvo siempre el mazo nuevo




      /*  if (getDamageEnemy() != 0) {
            return player.getInDeck();
        } else {
            if (player.getInDeck().size() <= damage) {
                Action huebo_de_laura = new InflictWounds(player);
                huebo_de_laura.executeAction();
            } else {
                Action huebo_de_laura = new RemoveCardForEnemyAttack(player);
                huebo_de_laura.executeAction();

            }
        }*/
        //reestablishment(); //CAMBIAR VISTA
    }


}

//necesito:
// la camtidad de cartasd el mazo de habilidad,
// el daño de todos los orcos del campo,
// las cartas restates se van al mazo de descarte (suponogo)
// las heridas el heroe
// la vida del heroe
}
