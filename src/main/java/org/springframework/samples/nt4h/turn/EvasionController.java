package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.turn.exceptions.WhenEvasionDiscardAtLeast2Exception;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/evasion")
public class EvasionController {

    private final String PAGE_EVASION = "redirect:/evasion";
    private final String VIEW_EVASION = "turns/evasionPhase";
    public final String NEXT_TURN = "redirect:/turns";
    private final UserService userService;
    private final TurnService turnService;
    private final PlayerService playerService;
    private final GameService gameService;

    @Autowired
    public EvasionController(UserService userService, TurnService turnService, PlayerService playerService, GameService gameService) {
        this.userService = userService;
        this.turnService = turnService;
        this.playerService = playerService;
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

    @GetMapping
    public String getEvasion() {
        return VIEW_EVASION;
    }

    @PostMapping
    public String postEvasion(@Valid Turn turn) throws NoCurrentPlayer {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        Turn oldTurn = turnService.getTurnsByPhaseAndPlayerId(Phase.EVADE, player.getId());
        oldTurn.addAbility(turn.getCurrentAbility());
        turnService.saveTurn(oldTurn);
        player.getDeck().discardCardOnHand(turn.getCurrentAbility());
        playerService.savePlayer(player);
        return PAGE_EVASION;
    }

    @GetMapping("/next")
    public String nextTurn() throws WhenEvasionDiscardAtLeast2Exception {
        Player player = getPlayer();
        Game game = getGame();
        if (player != getGame().getCurrentPlayer())
            return NEXT_TURN;
        Turn turn = turnService.getTurnsByPhaseAndPlayerId(Phase.EVADE, player.getId());
        if (turn.getUsedAbilities().size() < 2)
            throw new WhenEvasionDiscardAtLeast2Exception();
        game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.MARKET, player.getId()));
        gameService.saveGame(game);
        return NEXT_TURN;
    }
}
