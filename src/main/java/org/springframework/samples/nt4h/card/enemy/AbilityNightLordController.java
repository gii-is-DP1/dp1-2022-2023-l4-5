package org.springframework.samples.nt4h.card.enemy;

import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.turn.Turn;
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
@RequestMapping("/abilities")
public class AbilityNightLordController {


    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final String PAGE_ABILITIES = "redirect:/abilities";

    private final UserService userService;
    private final AbilityService abilityService;
    private final EnemyService enemyService;
    private final DeckService deckService;
    private final CacheManager cacheManager;


    public AbilityNightLordController(UserService userService, AbilityService abilityService, EnemyService enemyService, DeckService deckService, CacheManager cacheManager) {
        this.userService = userService;
        this.abilityService = abilityService;
        this.enemyService = enemyService;
        this.deckService = deckService;
        this.cacheManager = cacheManager;
    }

    @ModelAttribute("loggedUser")
    private User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("game")
    private Game getGame() {
        return getLoggedUser().getGame();
    }

    @ModelAttribute("currentPlayer")
    private Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("loggedPlayer")
    private Player getLoggedPlayer() {
        return getLoggedUser().getPlayer();
    }

    // Fase de ataque de héroe.
    @GetMapping("/gurdrug/{cardId}")
    public String gurdrug(@PathVariable("cardId") int cardId) {
        Player currentPlayer = getCurrentPlayer();
        deckService.fromDeckToDiscard(currentPlayer, currentPlayer.getDeck());
        AbilityInGame currentAbility = abilityService.getAbilityInGameById(cardId);
        return PAGE_ABILITIES + currentAbility.getAbility().getPathName();
    }

    // Fase de ataque de héroe.
    @GetMapping("/roghkiller/{cardId}")
    public String roghkiller(@PathVariable("cardId") int cardId, HttpSession session) {
        Game game = getGame();
        // Añade un punto de fortaleza a cada orco.
        AbilityInGame ability = abilityService.getAbilityInGameById(cardId);
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
        return PAGE_ABILITIES + ability.getAbility().getPathName();
    }

    @GetMapping("/shriekknifer/{cardId}")
    public String shriekknifer(@PathVariable("cardId") int cardId) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        AbilityInGame ability = abilityService.getAbilityInGameById(cardId);
        // Recupera una carta si el ataque es 1.
        if (ability.getAttack() == 1)
            deckService.fromDiscardToDeck(currentPlayer.getDeck());
        return PAGE_ABILITIES + ability.getAbility().getPathName();
    }

}
