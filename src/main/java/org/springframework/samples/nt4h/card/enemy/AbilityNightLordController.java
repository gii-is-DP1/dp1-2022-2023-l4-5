package org.springframework.samples.nt4h.card.enemy;

import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Los señores de la noche que hay disponibles en el juego son:
 * - Gurdrug.
 * - Roghkiller.
 * - Shriekknifer
 */
@Controller
@RequestMapping("/enemies/nightLord")
public class AbilityNightLordController {

    private final String PAGE_ENEMY_ATTACK = "redirect:/enemyAttack";
    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final String PAGE_LOSE_CARD = "redirect:/loseCard";
    private final String VIEW_LOSE_CARD = "abilities/loseCard";
    private final String VIEW_CHOSE_ENEMY = "abilities/choseEnemy";
    private final UserService userService;
    private final AbilityService abilityService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameService gameService;
    private final EnemyService enemyService;

    public AbilityNightLordController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService, EnemyService enemyService) {
        this.userService = userService;
        this.abilityService = abilityService;
        this.playerService = playerService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.enemyService = enemyService;
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("game")
    public Game getGame() {
        return getLoggedUser().getGame();
    }

    @ModelAttribute("currentPlayer")
    public Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("logggedPlayer")
    public Player getLoggedPlayer() {
        return getLoggedUser().getPlayer();
    }

    @GetMapping("/gurdrug")
    public String gurdrug(HttpSession session) {
        session.setAttribute("loseAnotherCard", 1);
        return "enemy/gurdrug";
    }

    @GetMapping("/roghkiller")
    public String roghkiller(HttpSession session) {
        Game game = getGame();
        // Añade un punto de fortaleza a cada orco.
        List<EnemyInGame> enemies = game.getActualOrcs();
        if (session.getAttribute("alreadyAddedHealthOrc") != null)
            return PAGE_ENEMY_ATTACK;
        for (var i = 0; i < enemies.size(); i++) {
            EnemyInGame enemy = enemies.get(i);
            if (!enemy.isNightLord()) {
                enemy.setActualHealth(enemy.getActualHealth() + 1);
                enemyService.saveEnemyInGame(enemy);
            }
        }
        return PAGE_ENEMY_ATTACK;
    }

    @GetMapping("/shriekknifer/{cardId}")
    public String shriekknifer(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        AbilityInGame ability = abilityService.getAbilityInGameById(cardId);
        // Recupera una carta si el ataque es 1.
        if (ability.getAttack() == 1) {
            Deck deck = currentPlayer.getDeck();
            AbilityInGame card = deck.getInDeck().get(0);
            deck.getInDeck().remove(card);
            deck.getInHand().add(card);
            playerService.savePlayer(currentPlayer);
        }
        // TODO: mejorar URL.
        return "/abilities/" + ability.getAbility().getRole() + "/" + ability.getId();
    }

}
