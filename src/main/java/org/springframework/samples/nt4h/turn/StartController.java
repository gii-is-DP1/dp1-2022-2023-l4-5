package org.springframework.samples.nt4h.turn;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.turn.exceptions.WithOutPhaseException;
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
    private Boolean isChosen = false;
    private final CacheManager cacheManager;


    @Autowired
    public StartController(UserService userService, TurnService turnService, Advise advise, CacheManager cacheManager) {
        this.userService = userService;
        this.turnService = turnService;
        this.advise = advise;
        this.cacheManager = cacheManager;
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
        cacheManager.deleteEndAttackHero(session);
        cacheManager.deleteEndAttackEnemy(session);
        if (!isChosen) {
            advise.choose(getGame());
            isChosen = true;
        }
        return VIEW_CHOOSE_EVASION;
    }

    @PostMapping
    public String selectEvasion(Turn turn,  HttpSession session) throws NoCurrentPlayer, WithOutPhaseException {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        Game game = getGame();
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        if (turn.getPhase() == null)
            throw new WithOutPhaseException();
        isChosen = false;
        turnService.chooseAttackOrEvasion(player, turn.getPhase(), game);
        advise.passPhase(game);
        return NEXT_TURN;
    }
}
