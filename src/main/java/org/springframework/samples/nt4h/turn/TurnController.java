package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/turns")
public class TurnController {


    private final UserService userService;

    private final String PAGE_EVADE = "redirect:/evasion";
    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final String PAGE_ENEMY_ATTACK = "redirect:/enemyAttack";
    private final String PAGE_MARKET = "redirect:/market";
    private final String PAGE_RESUPPLY = "redirect:/reestablishment/addCards";
    private final String PAGE_LOBBY = "redirect:/games/";

    @Autowired
    public TurnController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User getUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        return userService.getLoggedUser().getPlayer();
    }

    @ModelAttribute("game")
    public Game getGame() {
        return getUser().getGame();
    }

    @ModelAttribute("turn")
    public Turn getTurn() {
        return getGame().getCurrentTurn();
    }


    @GetMapping()
    public String enterInGame() {
        Phase phase = getTurn().getPhase();
        if (phase.equals(Phase.EVADE)) return PAGE_EVADE;
        else if (phase.equals(Phase.HERO_ATTACK)) return PAGE_HERO_ATTACK;
        else if (phase.equals(Phase.ENEMY_ATTACK)) return PAGE_ENEMY_ATTACK;
        else if (phase.equals(Phase.MARKET)) return PAGE_MARKET;
        else if (phase.equals(Phase.RESUPPLY)) return PAGE_RESUPPLY;
        else return PAGE_LOBBY;
    }
}
