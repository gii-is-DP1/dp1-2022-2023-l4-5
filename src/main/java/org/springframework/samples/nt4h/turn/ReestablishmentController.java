package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.exceptions.EnoughEnemiesException;
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


    public final String VIEW_REESTABLISHMENT = "turns/reestablishmentPhase";
    private final Advise advise;

    @Autowired
    public ReestablishmentController(UserService userService, PlayerService playerService) {
        this.playerService = playerService;
        this.userService = userService;
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

    @ModelAttribute("message")
    public String getMessage() {
        return advise.getMessage();
    }

    @ModelAttribute("messageType")
    public String getMessageType() {
        return advise.getMessageType();
    }

    @PostMapping("/addCards")
    public String takeNewAbilitiesAndEnemies() {
        try {
            playerService.takeNewCard(getPlayer());
            playerService.restoreEnemyLife(getEnemiesInBattle());
            playerService.addNewEnemiesToBattle(getEnemiesInBattle(), getAllEnemies(), getGame());
        } catch (EnoughEnemiesException e) {
            return advise.sendError("No te faltan orcos.", VIEW_REESTABLISHMENT);
        }
        return reestablishmentAddCards();
    }

    @GetMapping("/removeCards")
    public String reestablishmentNextTurn() {
        return "redirect:/nextTurn";
    }

    @PostMapping("/removeCards")
    public String removeHandAbilitiesIntoDiscard(Integer cardId) {
            playerService.removeAbilityCards(cardId, getPlayer());
        return reestablishmentNextTurn();
    }
}
