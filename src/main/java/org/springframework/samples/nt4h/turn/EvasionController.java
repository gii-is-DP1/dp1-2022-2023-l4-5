package org.springframework.samples.nt4h.turn;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/evasion")
public class EvasionController {

    public final String VIEW_CHOOSE_EVASION = "turns/actionDecision";
    public final String NEXT_TURN = "redirect:/turns/nextTurn";
    private final UserService userService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameService gameService;


    @Autowired
    public EvasionController(UserService userService, PlayerService playerService, TurnService turnService, GameService gameService) {
        this.playerService = playerService;
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
    }

    // TODO: Si el usuario no tiene el jugador, no podrá interactuar con la pantalla.
    // TODO: El nextPlayer deberá llevar a la acción que está.

    @ModelAttribute("game")
    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        // return getGame().getPlayer();
        return userService.getLoggedUser().getPlayer();
    }
    @ModelAttribute("phases")
    public List<Phase> getPhases() {
        Player player = getPlayer();
        turnService.getTurnsByPhaseAndPlayerId(Phase.EVADE, player.getId());
        return Lists.newArrayList(Phase.EVADE, Phase.HERO_ATTACK);
    }

    @GetMapping
    public String chooseEvasion() {
        return VIEW_CHOOSE_EVASION;
    }

    @PostMapping
    public String selectEvasion(Phase phase) {
        Player player = getPlayer();
        Turn turn = turnService.getTurnsByPhaseAndPlayerId(phase, player.getId());
        gameService.saveGame(getGame().toBuilder().currentTurn(turn).build()); // TODO: almacenar los cuatro turnos que toquen.
        if (phase == Phase.EVADE) {
            player.setHasEvasion(false);
            playerService.savePlayer(player);
        }
        return NEXT_TURN;
    }


}
