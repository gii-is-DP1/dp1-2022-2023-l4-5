package org.springframework.samples.nt4h.turn;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/start")
public class StartController {

    public final String VIEW_CHOOSE_EVASION = "turns/actionDecision";
    public final String NEXT_TURN = "redirect:/turns";
    private final UserService userService;
    private final TurnService turnService;
    private final Advise advise;


    @Autowired
    public StartController(UserService userService, TurnService turnService, Advise advise) {
        this.userService = userService;
        this.turnService = turnService;
        this.advise = advise;
    }

    @ModelAttribute("game")
    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    @ModelAttribute("currentPlayer")
    public Player getPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        User loggedUser = getLoggedUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(Statistic.createStatistic()).build();
    }

    @ModelAttribute("newTurn")
    public Turn getNewTurn() {
        return new Turn();
    }

    @ModelAttribute("turns")
    public List<Phase> getTurns() {
        return Lists.newArrayList(Phase.EVADE, Phase.HERO_ATTACK);
    }

    @ModelAttribute("chat")
    public Message getChat() {
        return new Message();
    }

    @GetMapping
    public String chooseEvasion(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
        advise.getMessage(session, modelMap);
        advise.keepUrl(session, request);
        if (session.getAttribute("choose") == null) {
            advise.choose(getGame());
            session.setAttribute("choose", true);
        }
        advise.choose(getGame());
        return VIEW_CHOOSE_EVASION;
    }

    @PostMapping
    public String selectEvasion(Turn turn,  HttpSession session) throws NoCurrentPlayer {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        Game game = getGame();
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        session.removeAttribute("choose");
        turnService.chooseAttackOrEvasion(player, turn.getPhase(), game);
        advise.passPhase(game);
        return NEXT_TURN;
    }
}
