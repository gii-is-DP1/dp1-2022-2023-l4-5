package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.RemoveCardFromHandToDiscard;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
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

    @GetMapping("/addCards")
    public String reestablishmentAddCards() {
        playerService.restoreEnemyLife(getEnemiesInBattle());
        playerService.addNewEnemiesToBattle(getEnemiesInBattle(), getAllEnemies(), getGame());
        return VIEW_REESTABLISHMENT;
    }

    @PostMapping("/addCards")
    public String takeNewAbility(Integer cardId) {
            Player player = getPlayer();
            Deck deck = player.getDeck();
            Action removeToDiscard = new RemoveCardFromHandToDiscard(deck, cardId);
            removeToDiscard.executeAction();
            deck.takeNewCard(); // TODO: cambiar a otro lugar,
        return reestablishmentAddCards();
    }

    @GetMapping("/removeCards")
    public String reestablishmentNextTurn() {
        return "redirect:/nextTurn";
    }

}
