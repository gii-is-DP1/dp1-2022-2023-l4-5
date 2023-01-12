package org.springframework.samples.nt4h.card.enemy;

import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.CacheManager;
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
    private final DeckService deckService;
    private final CacheManager cacheManager;

    public AbilityNightLordController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService, EnemyService enemyService, DeckService deckService, CacheManager cacheManager) {
        this.userService = userService;
        this.abilityService = abilityService;
        this.playerService = playerService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.enemyService = enemyService;
        this.deckService = deckService;
        this.cacheManager = cacheManager;
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

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        return getLoggedUser().getPlayer();
    }

    // Fase de ataque de héroe.
    @GetMapping("/gurdrug/{cardId}")
    public String gurdrug(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        deckService.fromDeckToDiscard(currentPlayer, currentPlayer.getDeck());
        AbilityInGame currentAbility = abilityService.getAbilityInGameById(cardId);
        return "/abilities/" + currentAbility.getAbility().getRole() + "/" + currentAbility.getId();
    }

    // Fase de ataque de héroe.
    @GetMapping("/roghkiller/{cardId}")
    public String roghkiller(@PathVariable("cardId") int cardId, HttpSession session) {
        Game game = getGame();
        // Añade un punto de fortaleza a cada orco.
        AbilityInGame ability = abilityService.getAbilityInGameById(cardId);
        String url = "redirect:/abilities/" + ability.getAbility().getRole() + "/" + ability.getId();
        List<EnemyInGame> enemies = game.getActualOrcs();
        if (!cacheManager.hasAddedLifeToOrcs(session)) {
            for (var i = 0; i < enemies.size(); i++) {
                EnemyInGame enemy = enemies.get(i);
                if (!enemy.isNightLord()) {
                    enemy.setActualHealth(enemy.getActualHealth() + 1);
                    enemyService.saveEnemyInGame(enemy);
                }
            }
            cacheManager.setHasAddedLifeToOrcs(session);
        }



        return url;
    }

    @GetMapping("/shriekknifer/{cardId}")
    public String shriekknifer(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        AbilityInGame ability = abilityService.getAbilityInGameById(cardId);
        // Recupera una carta si el ataque es 1.
        if (ability.getAttack() == 1)
            deckService.fromDiscardToDeck(currentPlayer.getDeck());
        // TODO: mejorar URL.
        return "/abilities/" + ability.getAbility().getRole() + "/" + ability.getId();
    }

}
