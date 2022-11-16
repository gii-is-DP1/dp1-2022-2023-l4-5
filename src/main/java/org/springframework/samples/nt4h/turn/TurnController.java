package org.springframework.samples.nt4h.turn;

import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
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
@RequestMapping("/turn")
public class TurnController {
    private final TurnService turnservice;
    private final GameService gameService;
    private final HeroService heroService;
    private final PlayerService playerService;

    private static final String VIEW_TURN_START = "turn/turnStart";
    private static final String VIEW_TURN_HERO_ATTACK = "turn/hero_attack";

    private static final String VIEW_TURN_RESUPLY= "turn/resuply";

    public TurnController(GameService gameService, UserService userService, HeroService heroService, PlayerService playerService, TurnService turnService) {
        this.gameService = gameService;
        this.heroService = heroService;
        this.playerService= playerService;
        this.turnservice= turnService;
    }
    @GetMapping("/{turnId}/{playerId}/start")
    public String startTurn(@PathVariable("turnId") int turnId, @PathVariable("playerId") int playerId, ModelMap model) {
        Turn turn = turnservice.getTurnByID(turnId);
        Player player= playerService.getPlayerById(playerId);
        model.put("turn", turn);
        model.put("player",player);
        return VIEW_TURN_START;
    }
    @PostMapping(value = "/{turnId}/{playerId}/start")
    public String processChoiceEvade(@Valid Turn turn, BindingResult result) {
        turnservice.saveTurn(turn);
        if(turn.getPhase()== Phase.HERO_ATTACK){
            return VIEW_TURN_HERO_ATTACK;
        }
        return VIEW_TURN_RESUPLY;
    }

}
