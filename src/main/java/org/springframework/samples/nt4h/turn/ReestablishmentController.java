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
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

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
        return getGame().getPlayer();
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

    @GetMapping()
    public String reestablishment() {
        return VIEW_REESTABLISHMENT;
    }

    @PostMapping
    public String putAbilitiesIntoHandDeck() {
        try {
            takeNewCard();
        } catch(EnoughCardsException exc) {
            return sendError("No te faltan cartas.", VIEW_REESTABLISHMENT);
        }
        resetMessage();
        return reestablishment();
    }

    @PostMapping
    public String removeHandAbilitiesIntoDiscard(Integer cardId) {
        try {
            removeAbilityCards(cardId);
        } catch(EnoughCardsException exc) {
            return sendError("No te faltan cartas.", VIEW_REESTABLISHMENT);
        }
        resetMessage();
        return reestablishment();
    }

    @PostMapping
    public String addNewEnemies() {
        try {
            addNewEnemiesToBattle();
        } catch(EnoughEnemiesException exc) {
            return sendError("No faltan orcos.", VIEW_REESTABLISHMENT);
        }
        resetMessage();
        return reestablishment();
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

    public void takeNewCard() throws EnoughCardsException {
        Player player = getPlayer();
        for(int i = 0; i < 3; i++) {
            if(getHandDeckByPlayer().size() < 3) {
                Action takeNewCard = new TakeCardFromAbilityPile(player);
                takeNewCard.executeAction();
            } else
                throw new EnoughCardsException();
        }
    }

    private void removeAbilityCards(Integer cardId) throws EnoughCardsException {
        Player player = getPlayer();
        while (getHandDeckByPlayer().size() > 3) {
            if (getHandDeckByPlayer().size() > 3) {
                Action removeToDiscard = new RemoveCardFromHandToDiscard(player, cardId);
                removeToDiscard.executeAction();
            } else
                throw new EnoughCardsException();
        }
    }

    private void addNewEnemiesToBattle() throws EnoughEnemiesException {
        List<EnemyInGame> enemies = getEnemiesInBattle();
        List<EnemyInGame> allOrcs = getAllEnemies();
        if(enemies.size() == 1 || enemies.size() == 2) {
            enemies.add(allOrcs.get(1));
            allOrcs.remove(1);
        } else if(enemies.size() == 0) {
            enemies = getAllEnemies().stream().limit(3).collect(Collectors.toList());
            allOrcs.removeAll(enemies);
        } else
            throw new EnoughEnemiesException();
    }


}
