package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enemyAttack")
public class EnemyAttackController {


    private UserService userService;
    private final TurnService turnService;
    private final GameService gameService;

    private final AttackEnemyService attackEnemyService; //este debe cambiar por Service Enemey supongo
    public final String VIEW_ATTACK = "turns/attackPhase";
    public final String NEXT_TURN = "redirect:/turns";

    @ModelAttribute("user")
    public User getUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        return userService.getLoggedUser().getPlayer();
    }

    @ModelAttribute("game")
    public Game getGame() {
        return getPlayer().getGame();
    }

    @ModelAttribute("newTurn")
    public Turn getNewTurn() {
        return new Turn();
    }



    @Autowired
    public EnemyAttackController(UserService userService, TurnService turnService, GameService gameService, AttackEnemyService attackEnemyService) {
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.attackEnemyService = attackEnemyService;
    }

    @GetMapping
    public String getEnemyAttack(ModelMap model) {
        model.put("damage", attackEnemyService.attackEnemyToActualPlayer(getGame()));
        return VIEW_ATTACK;
    }

    @GetMapping("/next")
    public String nextTurn() {
        Player player = getPlayer();
        Game game = getGame();
        if(player == getGame().getCurrentPlayer()) {
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.MARKET, player.getId()));
            gameService.saveGame(game);
        }
        return NEXT_TURN;
    }
}
