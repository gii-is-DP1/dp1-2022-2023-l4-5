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
@RequestMapping("/abilities")
public class AbilityKnightController {

    private final String PAGE_MAKE_DAMAGE = "redirect:/heroAttack/makeDamage";
    private final String PAGE_END_ATTACK = "redirect:/heroAttack/next";
    private final UserService userService;
    private final DeckService deckService;
    private final CacheManager cacheManager;
    private final StatisticService statisticService;

    public AbilityKnightController(UserService userService, DeckService deckService, CacheManager cacheManager, StatisticService statisticService) {
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
    @GetMapping("/brutalAttack")
    private String brutalAttack() {
        Player currentPlayer = getCurrentPlayer();
        // Pierde una carta.
        deckService.fromDeckToDiscard(currentPlayer, currentPlayer.getDeck());
        return PAGE_MAKE_DAMAGE;
    }

    // Carga con escudo.
    @GetMapping("/shieldCharge")
    private String shieldCharge(HttpSession session) {
        // Aumenta el valor de la defensa en dos.
        cacheManager.setDefend(session, 2);
        return PAGE_MAKE_DAMAGE;
    }

    // Doble espadazo.
    @GetMapping("/doubleSlash")
    private String doubleSlash() {
        Player currentPlayer = getCurrentPlayer();
        // Elimina una carta de la mano.
        deckService.fromDeckToDiscard(currentPlayer, currentPlayer.getDeck());
        return PAGE_MAKE_DAMAGE;
    }

    // Escudo.
    @GetMapping("/shield")
    private String shield(HttpSession session) {
        // El enemigo seleccionado no podrá hacer daño.
        cacheManager.addPreventDamageFromEnemies(session);
        // Termina el turno.
        return PAGE_END_ATTACK;
    }

    // Espadazo.
    @GetMapping("/slash")
    private String slash(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Comprueba si es el primer slash.
        if (cacheManager.isFirstSlash(session)) {
            cacheManager.setFirstSlash(session);
            // Si lo es, roba una carta.
            deckService.fromDiscardToDeck(currentPlayer.getDeck());
        }
        return PAGE_MAKE_DAMAGE;
    }

    // Paso atrás.
    @GetMapping("/stepBack")
    private String stepBack() {
        Player currentPlayer = getCurrentPlayer();
        // Roba dos cartas.
        deckService.fromDiscardToDeck(currentPlayer.getDeck(), 2);
        return PAGE_MAKE_DAMAGE;
    }

    // Todo o nada.
    @GetMapping("/allOrNothing")
    private String allOrNothing(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Roba una carta.
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        deckService.specificCardFromDiscardToDeck(deck, abilityInGame);
        // Agrega ese daño a la carta.
        cacheManager.addAttack(session, abilityInGame.getAttack());
        return PAGE_MAKE_DAMAGE;
    }

    // Voz de aliento
    @GetMapping("/voiceOfEncouragement")
    private String voiceOfEncouragement() {
        Player currentPlayer = getCurrentPlayer();
        // cada jugador roba dos cartas.
        for (Player player : getGame().getPlayers()) {
            deckService.fromDiscardToDeck(player.getDeck(), 2);
        }
        // Roba una carta.
        deckService.fromDiscardToDeck(currentPlayer.getDeck());
        // Gana una ficha de gloria.
        statisticService.gainGlory(currentPlayer, 1);
        return PAGE_MAKE_DAMAGE;
    }
}
