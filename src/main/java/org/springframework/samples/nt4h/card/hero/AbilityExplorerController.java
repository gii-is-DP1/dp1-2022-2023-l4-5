package org.springframework.samples.nt4h.card.hero;

import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.StatisticService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Las habilidades de explorador son:
 * - Compañero lobo.
 * - Disparo certero.
 * - Disparo rápido.
 * - En la diana.
 * - Lluvia de flechas.
 * - Recoger flechas.
 * - Supervivencia.
 */
@Controller
@RequestMapping("/abilities/explorer")
public class AbilityExplorerController {

    private final String PAGE_MAKE_DAMAGE = "redirect:/heroAttack/makeDamage";
    private final String VIEW_CHOSE_ENEMY = "abilities/choseEnemy";
    private final UserService userService;
    private final PlayerService playerService;
    private final CacheManager cacheManager;
    private final DeckService deckService;
    private final StatisticService statisticService;

    public AbilityExplorerController(UserService userService, PlayerService playerService, CacheManager cacheManager, DeckService deckService, StatisticService statisticService) {
        this.userService = userService;
        this.playerService = playerService;
        this.cacheManager = cacheManager;
        this.deckService = deckService;
        this.statisticService = statisticService;
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

    // Compañero lobo.
    @GetMapping("/fellowWolf/{cardId}")
    private String fellowWolf(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Aumenta el valor de la defensa en dos.
        cacheManager.setDefend(session, 2);
        return PAGE_MAKE_DAMAGE;
    }

    // Disparo certero.
    @GetMapping("/preciseShot/{cardId}")
    private String preciseShot(@PathVariable("cardId") Integer cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Debería de ser un efecto
        // Pierde una carta.
        deckService.fromDeckToDiscard(currentPlayer.getDeck());
        // Finaliza el ataque.
        return PAGE_MAKE_DAMAGE + "/next";
    }

    // Disparo rápido.
    @GetMapping("/rapidFire/{cardId}")
    private String rapidFire(@PathVariable("cardId") Integer cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Debería de ser un efecto
        // Tomo una primera carta.
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        while (abilityInGame.getAbility().getName().equals("Disparo rápido")) {
            // Añade el daño.
            cacheManager.addAttack(session, abilityInGame.getAbility().getAttack());
            // Elimina la carta.
            deckService.specificCardFromDeckToDiscard(deck, abilityInGame);
            // Tomo una nueva carta.
            abilityInGame = deck.getInDeck().get(0);
        }
        // Coloco la carta al fondo del deck.
        deckService.putFirstCardAtBottomOfDeck(deck);
        playerService.savePlayer(currentPlayer);
        return PAGE_MAKE_DAMAGE;
    }

    // En la diana.
    @GetMapping("/target/{cardId}")
    private String target(@PathVariable("cardId") Integer cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Debería de ser un efecto
        // Gana una ficha de gloria.
        statisticService.gainGlory(currentPlayer, 1);
        // Pierde una carta.
        deckService.fromDeckToDiscard(currentPlayer.getDeck());
        playerService.savePlayer(currentPlayer);
        return PAGE_MAKE_DAMAGE;
    }

    // Lluvia de flechas.
    @GetMapping("/arrowRain/{cardId}")
    private String arrowRain(@PathVariable("cardId") Integer cardId, HttpSession session, ModelMap model) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Debería de ser un efecto.
        model.put("name", "enemyAlsoAttacked");
        return VIEW_CHOSE_ENEMY;
    }

    // Recoger flechas.
    @GetMapping("/collectArrows/{cardId}")
    private String collectArrows(@PathVariable("cardId") Integer cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Debería de ser un efecto
        // Recupera una carta de disparo rápido de la pila de descarte.
        Deck deck = currentPlayer.getDeck();
        deck.getInDiscard().stream().anyMatch(a -> a.getAbility().getName().equals("Disparo rápido"));
        // Gana una moneda.
        statisticService.gainGlory(currentPlayer, 1);
        return PAGE_MAKE_DAMAGE;
    }

    // Supervivencia
    @GetMapping("/survival/{cardId}")
    private String survival(@PathVariable("cardId") Integer cardId, HttpSession session, ModelMap model) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Debería de ser un efecto
        // Gana una carta de supervivencia.
        model.put("name", "getOutEnemy");
        return VIEW_CHOSE_ENEMY;
    }


}
