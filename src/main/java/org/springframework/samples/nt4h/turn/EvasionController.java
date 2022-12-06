package org.springframework.samples.nt4h.turn;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.DropCardFromHand;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.Phaser;

@Controller
@RequestMapping("/evasion")
public class EvasionController {

    public final String VIEW_CHOOSE_EVASION = "turns/actionDecision";
    public final String NEXT_TURN = "redirect:/turns/nextTurn";
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

    @ModelAttribute("message")
    public String getMessage() {
        String message = advise.getMessage();
        advise.resetMessage();
        return message;
    }

    @ModelAttribute("messageType")
    public String getMessageType() {
        String messageType = advise.getMessageType();
        advise.setMessageType("");
        return messageType;
    }

    @ModelAttribute("turn")
    public Turn getTurn() {
        return getGame().getCurrentTurn();
    }

    @ModelAttribute("turns")
    public List<Turn> getPhases() {
        return Lists.newArrayList(
            turnService.getTurnsByPhaseAndPlayerId(Phase.MARKET, getPlayer().getId()),
            turnService.getTurnsByPhaseAndPlayerId(Phase.HERO_ATTACK, getPlayer().getId()));
    }

    @GetMapping
    public String chooseEvasion() {
        return VIEW_CHOOSE_EVASION;
    }

    @PostMapping
    public String selectEvasion(Turn turn) {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            return advise.sendError("No puedes seleccionar si atacar o evadir.",chooseEvasion());
        gameService.saveGame(getGame().toBuilder().currentTurn(turn).build());
        if (turn.getPhase() == Phase.MARKET) {
            player.setHasEvasion(false);
            player.setNextPhase(Phase.MARKET);
            Action action = new DropCardFromHand(loggedPlayer, null); // TODO: Eliminar dos cartas
            action.executeAction();
            playerService.savePlayer(player);
        } else {
            player.setNextPhase(Phase.HERO_ATTACK);
            playerService.savePlayer(player);
        }
        return NEXT_TURN;
    }
}
