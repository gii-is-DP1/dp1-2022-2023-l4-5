package org.springframework.samples.nt4h.turn;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/start")
public class StartController {

    public final String VIEW_CHOOSE_EVASION = "turns/actionDecision";
    public final String NEXT_TURN = "redirect:/turns";
    private final UserService userService;
    private final TurnService turnService;


    @Autowired
    public StartController(UserService userService, TurnService turnService) {
        this.userService = userService;
        this.turnService = turnService;
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
    public String chooseEvasion(HttpSession session, ModelMap modelMap) {
        getMessage(session, modelMap);
        return VIEW_CHOOSE_EVASION;
    }

    @PostMapping
    public String selectEvasion(Turn turn) throws NoCurrentPlayer {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        turnService.chooseAttackOrEvasion(player, turn.getPhase(), getGame());
        return NEXT_TURN;
    }

    public void getMessage(HttpSession session, ModelMap model) {
        Object message = session.getAttribute("message");
        Object messageType = session.getAttribute("messageType");
        if (message != null) {
            model.addAttribute("message", message);
            model.addAttribute("messageType", messageType);
            session.removeAttribute("message");
            session.removeAttribute("messageType");
        }
    }
}
