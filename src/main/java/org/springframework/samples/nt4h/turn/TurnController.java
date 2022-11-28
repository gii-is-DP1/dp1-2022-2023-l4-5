package org.springframework.samples.nt4h.turn;

import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/games/{gameId}/turns")
public class TurnController {

    private final TurnService turnService;
    private final GameService gameService;
    private final HeroService heroService;
    private final PlayerService playerService;
    private final UserService userService;

    private static final String VIEW_TURN_START = "turns/turnStart";
    private static final String VIEW_TURN_HERO_ATTACK = "turns/hero_attack";

    private static final String VIEW_TURN_RESUPLY= "turns/resuply";

    public TurnController(GameService gameService, HeroService heroService, PlayerService playerService, TurnService turnService, UserService userService) {
        this.gameService = gameService;
        this.heroService = heroService;
        this.playerService= playerService;
        this.turnService= turnService;
        this.userService = userService;
    }
    @GetMapping("/{turnId}/{playerId}/start")
    public String startTurn(@PathVariable("turnId") int turnId, @PathVariable("playerId") int playerId, ModelMap model) {
        Turn turn = turnService.getTurnByID(turnId);
        Player player= playerService.getPlayerById(playerId);
        model.put("turn", turn);
        model.put("player",player);
        return VIEW_TURN_START;
    }

    @PostMapping(value = "/{turnId}/{playerId}/start")
    public String processChoiceEvade(@Valid Turn turn) {
        turnService.saveTurn(turn);
        if(turn.getPhase()== Phase.HERO_ATTACK){
            return VIEW_TURN_HERO_ATTACK;
        }
        return VIEW_TURN_RESUPLY;
    }


}
