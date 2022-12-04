package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.RemoveCardFromHandToDiscard;
import org.springframework.samples.nt4h.action.TakeCardFromAbilityPile;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameController;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/reestablishment")
public class ReestablishmentController {

    private final UserService userService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameController gameController;
    private final AbilityService abilityService;
    private final EnemyService enemyService;

    public final String VIEW_REESTABLISHMENT = "turns/reestablishmentPhase";

    private String message;
    private String messageType;

    @Autowired
    public ReestablishmentController(UserService userService, PlayerService playerService, TurnService turnService, GameController gameController, AbilityService abilityService, EnemyService enemyService) {
        this.playerService = playerService;
        this.userService = userService;
        this.turnService = turnService;
        this.gameController = gameController;
        this.abilityService = abilityService;
        this.enemyService = enemyService;
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

    @ModelAttribute("enemiesInBattle")
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

    @GetMapping("/addCards")
    public String reestablishmentAddCards() {
        return VIEW_REESTABLISHMENT;
    }

    @PostMapping("/addCards")
    public String takeNewAbilitiesAndEnemies() {
        try {
            playerService.takeNewCard(getPlayer());
            playerService.restoreEnemyLife(getEnemiesInBattle());
            playerService.addNewEnemiesToBattle(getEnemiesInBattle(), getAllEnemies(), getGame());
        } catch(EnoughCardsException exc) {
            return sendError("No te faltan cartas.", VIEW_REESTABLISHMENT);
        } catch (EnoughEnemiesException e) {
            return sendError("No te faltan orcos.", VIEW_REESTABLISHMENT);
        }
        resetMessage();
        return reestablishmentAddCards();
    }

    @GetMapping("/removeCards")
    public String reestablishmentNextTurn() {
        return "redirect:/nextTurn";
    }

    @PostMapping("/removeCards")
    public String removeHandAbilitiesIntoDiscard(Integer cardId) {
        try {
            playerService.removeAbilityCards(cardId, getPlayer());
        } catch(EnoughCardsException exc) {
            return sendError("No te faltan cartas.", VIEW_REESTABLISHMENT);
        }
        resetMessage();
        return reestablishmentNextTurn();
    }

    //Métodos auxiliares
    public String sendError(String message, String redirect) {
        this.message = message;
        messageType = "danger";
        return redirect;
    }

    private void resetMessage() {
        this.message = "";
        this.messageType = "";
    }

}
