package org.springframework.samples.nt4h.turn;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.User;
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

    public final String VIEW_CHOOSE_EVASION = "evasion/chooseEvasion";
    private final UserService userService;
    private final PlayerService playerService;
    private final TurnService turnService;


    @Autowired
    public EvasionController(UserService userService, PlayerService playerService, TurnService turnService) {
        this.playerService = playerService;
        this.userService = userService;
        this.turnService = turnService;
    }

    @ModelAttribute("game")
    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        return getGame().getPlayer();
    }
    @ModelAttribute("phases")
    public List<Turn> getPhases() {
        Player player = getGame().getPlayer();
        return Lists.newArrayList(turnService.getTurnsByPhaseAndPlayerId(Phase.EVADE, player.getId()),
                turnService.getTurnsByPhaseAndPlayerId(Phase.HERO_ATTACK, player.getId()));
    }

    @GetMapping
    public String chooseEvasion() {
        return VIEW_CHOOSE_EVASION;
    }

    @PostMapping
    public String selectEvasion(Turn turn) {
        Player player = getPlayer();
        if (turn.getPhase() == Phase.EVADE) {
            player.setHasEvasion(false);
            playerService.savePlayer(player);
            return "redirect:/market";
        } else
            return "redirect:/attack";
    }


}
