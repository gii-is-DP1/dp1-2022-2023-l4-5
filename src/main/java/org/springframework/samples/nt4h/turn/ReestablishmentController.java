package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
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
@RequestMapping("/reestablishment")
public class ReestablishmentController {

    private final UserService userService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameService gameService;


    public final String VIEW_REESTABLISHMENT = "turns/reestablishmentPhase";
    public final String NEXT_TURN = "redirect:/turns";
    private final Advise advise;

    @Autowired
    public ReestablishmentController(UserService userService, PlayerService playerService, TurnService turnService, GameService gameService) {
        this.playerService = playerService;
        this.userService = userService;
        this.turnService = turnService;
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
        return getPlayer().getInHand();
    }

    @ModelAttribute("discardCards")
    public List<AbilityInGame> getDiscardDeckByPlayer() {
        return getPlayer().getInDiscard();
    }

    @ModelAttribute("abilityCards")
    public List<AbilityInGame> getAbilityDeckByPlayer() {
        return getPlayer().getInDeck();
    }

    @ModelAttribute("message")
    public String getMessage() {
        return advise.getMessage();
    }

    @ModelAttribute("messageType")
    public String getMessageType() {
        return advise.getMessageType();
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
    public String reestablishmentAddCards() {
        playerService.restoreEnemyLife(getEnemiesInBattle());
        playerService.addNewEnemiesToBattle(getEnemiesInBattle(), getAllEnemies(), getGame());
        return VIEW_REESTABLISHMENT;
    }

    @PostMapping
    public String takeNewAbilities(Turn newTurn) {
        AbilityInGame currentAbility = newTurn.getCurrentAbility();
        playerService.takeNewCard(getPlayer());
        playerService.removeAbilityCards(currentAbility.getId(), getPlayer());
        return reestablishmentAddCards();
    }

    @GetMapping("/turns")
    public String reestablishmentNextTurn() {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        Game game = getGame();
        if (loggedPlayer == player) {
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.EVADE, player.getId()));
            gameService.saveGame(game);
        }
        return NEXT_TURN;
    }

}
