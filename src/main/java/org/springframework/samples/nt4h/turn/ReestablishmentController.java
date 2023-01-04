package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reestablishment")
public class ReestablishmentController {

    private final UserService userService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final DeckService deckService;
    private final GameService gameService;

    public final String NEXT_TURN = "redirect:/turns";
    public final String VIEW_REESTABLISHMENT = "turns/reestablishmentPhase";
    private final Advise advise;

    @Autowired
    public ReestablishmentController(UserService userService, PlayerService playerService, TurnService turnService, DeckService deckService, GameService gameService) {
        this.playerService = playerService;
        this.userService = userService;
        this.turnService = turnService;
        this.deckService = deckService;
        this.gameService = gameService;
        this.advise = new Advise();
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

    @ModelAttribute("enemiesInBattle")
    public List<EnemyInGame> getEnemiesInBattle() {
        return getGame().getActualOrcs();
    }

    @ModelAttribute("allEnemiesInGame")
    public List<EnemyInGame> getAllEnemies() {
        return getGame().getAllOrcsInGame();
    }

    @ModelAttribute("handCards")
    public List<AbilityInGame> getHandDeckByPlayer() {
        return getPlayer().getDeck().getInHand();
    }

    @ModelAttribute("discardCards")
    public List<AbilityInGame> getDiscardDeckByPlayer() {
        return getPlayer().getDeck().getInDiscard();
    }

    @ModelAttribute("abilityCards")
    public List<AbilityInGame> getAbilityDeckByPlayer() {
        return getPlayer().getDeck().getInDeck();
    }

    @ModelAttribute("message")
    public String getMessage() {
        return advise.getMessage();
    }

    @ModelAttribute("messageType")
    public String getMessageType() {
        return advise.getMessageType();
    }

    @GetMapping()
    public String modifyEnemies() {
        playerService.restoreEnemyLife(getEnemiesInBattle());
        playerService.addNewEnemiesToBattle(getEnemiesInBattle(), getAllEnemies(), getGame());
        return VIEW_REESTABLISHMENT;
    }

    @PostMapping("/{cardId}")
    public String takeAndRemoveAbilities(@PathVariable Integer cardId) {
        Player player = getPlayer();
        deckService.takeNewCard(getPlayer(), player.getDeck());
        deckService.removeAbilityCards(cardId, getPlayer());
        return modifyEnemies();
    }

    @GetMapping("/next")
    public String nextTurn(Turn turn) throws NoCurrentPlayer {
        turnService.setNextPlayerAndNextTurn(getGame(), getPlayer(), getLoggedPlayer());
        return NEXT_TURN;
    }

}
