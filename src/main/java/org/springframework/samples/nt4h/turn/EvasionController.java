package org.springframework.samples.nt4h.turn;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/evasion")
public class EvasionController {

    public final String VIEW_CHOOSE_EVASION = "turns/actionDecision";
    public final String NEXT_TURN = "redirect:/turns";
    private final UserService userService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameService gameService;

    private final Advise advise = new Advise();


    @Autowired
    public EvasionController(UserService userService, PlayerService playerService, TurnService turnService, GameService gameService) {
        this.playerService = playerService;
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
    }

    @ModelAttribute("game")
    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("loggedPLayer")
    public Player getLoggedPlayer() {
        return userService.getLoggedUser().getPlayer();
    }

    @ModelAttribute("newTurn")
    public Turn getNewTurn() {
        return new Turn();
    }

    @ModelAttribute("turns")
    public List<Phase> getTurns() {
        return Lists.newArrayList(Phase.EVADE, Phase.HERO_ATTACK);
    }

    @GetMapping
    public String chooseEvasion() {
        return VIEW_CHOOSE_EVASION;
    }

    @PostMapping
    public String selectEvasion(Turn turn) throws NoCurrentPlayer {
        turnService.chooseAttackOrEvasion(getPlayer(), getLoggedPlayer(), turn.getPhase(), getGame());
        return NEXT_TURN;
    }
}
