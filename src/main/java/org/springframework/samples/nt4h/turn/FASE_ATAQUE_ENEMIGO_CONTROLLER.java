package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/enemyTurns")
public class FASE_ATAQUE_ENEMIGO_CONTROLLER {


    private UserService userService;

    private final FASE_DE_ATAQUE_ENEMIGO_SERVICIO fase_de_ataque_enemigo_servicio; //este debe cambiar por Service Enemey supongo

    //   public final String VIEW_ENEMYATTACK = "turns/enemyAttackPhase";
    public final String NEXT_TURN = "redirect:/turns";

    @Autowired
    public FASE_ATAQUE_ENEMIGO_CONTROLLER(FASE_DE_ATAQUE_ENEMIGO_SERVICIO fase_de_ataque_enemigo_servicio) {
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
