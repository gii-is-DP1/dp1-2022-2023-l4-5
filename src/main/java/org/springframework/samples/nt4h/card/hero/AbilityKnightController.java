package org.springframework.samples.nt4h.card.hero;

import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.StatisticService;
import org.springframework.samples.nt4h.turn.TurnService;
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
 * Las habilidades del caballero son.
 * - Ataque brutal.
 * - Carga con escudo.
 * - Doble espadazo.
 * - Escudo.
 * - Paso atrás.
 * - Todo o nada.
 * - Voz de aliento.
 */
@Controller
@RequestMapping("/abilities/knight")
public class AbilityKnightController {

    private final String PAGE_MAKE_DAMAGE = "redirect:/heroAttack/makeDamage";

    private final String VIEW_LOSE_CARD = "abilities/loseCard";
    private final String VIEW_CHOSE_ENEMY = "abilities/choseEnemy";
    private final UserService userService;
    private final DeckService deckService;
    private final CacheManager cacheManager;
    private final StatisticService statisticService;

    public AbilityKnightController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService, DeckService deckService, CacheManager cacheManager, StatisticService statisticService) {
        this.userService = userService;
        this.deckService = deckService;
        this.cacheManager = cacheManager;
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

    // Ataque brutal
    @GetMapping("/brutalAttack/{cardId}")
    private String brutalAttack(@PathVariable("cardId") int cardId) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Pierde una carta.
        deckService.fromDeckToDiscard(currentPlayer.getDeck());
        return VIEW_LOSE_CARD;
    }

    // Carga con escudo.
    @GetMapping("/shieldCharge/{cardId}")
    private String shieldCharge(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Aumenta el valor de la defensa en dos.
        cacheManager.setDefend(session, 2);
        return PAGE_MAKE_DAMAGE;
    }

    // Doble espadazo.
    @GetMapping("/doubleSlash/{cardId}")
    private String doubleSlash(@PathVariable("cardId") int cardId) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Elimina una carta de la mano.
        deckService.fromDeckToDiscard(currentPlayer.getDeck());
        return PAGE_MAKE_DAMAGE;
    }

    // Escudo.
    @GetMapping("/shield/{cardId}")
    private String shield(@PathVariable("cardId") int cardId, HttpSession session, ModelMap model) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Termina el turno.
        cacheManager.setNextUrl(session, PAGE_MAKE_DAMAGE + "/next");
        model.put("name", "preventDamageFrom");
        return VIEW_CHOSE_ENEMY;
    }

    // Espadazo.
    @GetMapping("/slash/{cardId}")
    private String slash(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Comprueba si es el primer slash.
        if (cacheManager.isFirstSlash(session)) {
            cacheManager.setFirstSlash(session);
            // Si lo es, roba una carta.
            deckService.fromDiscardToDeck(currentPlayer.getDeck());
        }
        return PAGE_MAKE_DAMAGE;
    }

    // Paso atrás.
    @GetMapping("/stepBack/{cardId}")
    private String stepBack(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Roba dos cartas.
        for (int i = 0; i < 2; i++) {
            deckService.fromDiscardToDeck(currentPlayer.getDeck());
        }
        return PAGE_MAKE_DAMAGE;
    }

    // Todo o nada.
    @GetMapping("/allOrNothing/{cardId}")
    private String allOrNothing(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Debería de ser un efecto
        // Roba una carta.
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        deckService.specificCardFromDiscardToDeck(deck, abilityInGame);
        // Agrega ese daño a la carta.
        cacheManager.addAttack(session, abilityInGame.getAttack());
        return PAGE_MAKE_DAMAGE;
    }

    // Voz de aliento
    @GetMapping("/voiceOfEncouragement/{cardId}")
    private String voiceOfEncouragement(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // cada jugador roba dos cartas.
        for (Player player : getGame().getPlayers()) {
            for (int i = 0; i < 2; i++) {
                deckService.fromDiscardToDeck(player.getDeck());
            }
        }
        // Roba una carta.
        deckService.fromDiscardToDeck(currentPlayer.getDeck());
        // Gana una ficha de gloria.
        statisticService.gainGlory(currentPlayer, 1);
        return PAGE_MAKE_DAMAGE;
    }
}
