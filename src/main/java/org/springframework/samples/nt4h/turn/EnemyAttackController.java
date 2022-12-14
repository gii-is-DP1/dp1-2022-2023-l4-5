package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enemyTurns")
public class EnemyAttackController {


    private UserService userService;

    private final TurnService fase_de_ataque_enemigo_servicio; //este debe cambiar por Service Enemey supongo

    //   public final String VIEW_ENEMYATTACK = "turns/enemyAttackPhase";
    public final String NEXT_TURN = "redirect:/turns";

    @Autowired
    public EnemyAttackController(TurnService fase_de_ataque_enemigo_servicio) {
        this.fase_de_ataque_enemigo_servicio = fase_de_ataque_enemigo_servicio;
    }

    @ModelAttribute("game")
    public Game getGame() { return userService.getLoggedUser().getGame(); }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/attackEnemy")
    public String takeNewAbilitiesAndEnemies() {
            fase_de_ataque_enemigo_servicio.attackEnemyToActualPlayer(getGame()); //:)
        return NEXT_TURN;  //fase de mercado  //Poner una nueva vista???
    }
}
