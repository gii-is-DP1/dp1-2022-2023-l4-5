package org.springframework.samples.nt4h.card.hero;

import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.statistic.StatisticService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Las habilidades del caballero son.
 * - Ataque brutal. Fufa
 * - Carga con escudo. Fufa
 * - Doble espadazo. Fufa
 * - Escudo. Fufa
 * - Paso atrás. Fufa
 * - Todoo o nada. Fufa
 * - Voz de aliento. Fufa
 */
@Controller
@RequestMapping("/abilities")
public class AbilityKnightController {

    private final static String PAGE_MAKE_DAMAGE = "redirect:/heroAttack/makeDamage";
    private final static String PAGE_END_ATTACK = "redirect:/heroAttack/next";
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
        System.out.println("Escudo" + cacheManager.getPreventDamageFromEnemies(session));
        // Termina el turno.
        cacheManager.setNextUrl(session, PAGE_END_ATTACK);
        System.out.println("Escudo" + cacheManager.getNextUrl(session));
        return PAGE_MAKE_DAMAGE;
    }

    // Espadazo.
    @GetMapping("/slash")
    private String slash(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Comprueba si es el primer slash.
        if (Boolean.TRUE.equals(cacheManager.isFirstSlash(session))) {
            cacheManager.setFirstSlash(session);
            // Si lo es, roba una carta.
            deckService.fromDeckToHand(currentPlayer, currentPlayer.getDeck());
        }
        return PAGE_MAKE_DAMAGE;
    }

    // Paso atrás.
    @GetMapping("/stepBack")
    private String stepBack() {
        Player currentPlayer = getCurrentPlayer();
        // Roba dos cartas.
        deckService.fromDeckToHand(currentPlayer, currentPlayer.getDeck(), 2);
        return PAGE_MAKE_DAMAGE;
    }

    // Todoo o nada.
    @GetMapping("/allOrNothing")
    private String allOrNothing(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Roba una carta.
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        deckService.specificCardFromDeckToHand(currentPlayer, deck, abilityInGame);
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
            deckService.fromDiscardToHand(player.getDeck(), 2);
        }
        // Roba una carta.
        deckService.fromDeckToHand(currentPlayer, currentPlayer.getDeck());
        // Gana una ficha de gloria.
        statisticService.gainGlory(currentPlayer, 1);
        return PAGE_MAKE_DAMAGE;
    }
}
