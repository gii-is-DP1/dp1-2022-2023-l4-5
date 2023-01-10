package org.springframework.samples.nt4h.card.hero;

import org.hibernate.stat.Statistics;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.Statistic;
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
import java.util.Collections;
import java.util.Optional;

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

    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final String PAGE_LOSE_CARD = "redirect:/loseCard";
    private final String VIEW_LOSE_CARD = "abilities/loseCard";
    private final String VIEW_CHOSE_ENEMY = "abilities/choseEnemy";
    private final UserService userService;
    private final AbilityService abilityService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameService gameService;

    public AbilityExplorerController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService) {
        this.userService = userService;
        this.abilityService = abilityService;
        this.playerService = playerService;
        this.turnService = turnService;
        this.gameService = gameService;
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

    // Compañero lobo.
    @GetMapping("/fellowWolf/{cardId}")
    private String fellowWolf(@PathVariable("cardId") Integer cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // TODO: comprobar que el id es correcto.
        // Aumenta el valor de la defensa en dos.
        if (session.getAttribute("defend") == null)
            session.setAttribute("defend", 2);
        else
            session.setAttribute("defend", (int) session.getAttribute("defend") + 2);
        //
        return PAGE_HERO_ATTACK;
    }

    // Disparo certero.
    @GetMapping("/preciseShot/{cardId}")
    private String preciseShot(@PathVariable("cardId") Integer cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // Pierde una carta.
        AbilityInGame abilityInGame = abilityService.getAbilityInGameById(cardId);
        Deck deck = currentPlayer.getDeck();
        deck.discardCardOnHand(abilityInGame);
        playerService.savePlayer(currentPlayer);
        // Finaliza el ataque.
        return PAGE_HERO_ATTACK + "/next";
    }

    // Disparo rápido.
    @GetMapping("/rapidFire/{cardId}")
    private String rapidFire(@PathVariable("cardId") Integer cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // Tomo una primera carta.
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        //deck.getInDeck().remove(abilityInGame);
        //deck.getInHand().add(abilityInGame);
        // Mientras la carta sea disparo rápido.
        while (abilityInGame.getAbility().getName().equals("Disparo rápido")) {
            // Añade el daño.
            if (session.getAttribute("attack") == null)
                session.setAttribute("attack", abilityInGame.getAttack());
            else
                session.setAttribute("attack", (int) session.getAttribute("attack") + 2);
            // Elimina la carta.
            deck.getInDeck().remove(abilityInGame);
            deck.getInDiscard().add(abilityInGame);
            // Tomo una nueva carta.
            abilityInGame = deck.getInDeck().get(0);
        }
        // Coloco la carta al fondo del deck.
        deck.getInDeck().remove(abilityInGame);
        deck.getInDeck().add(abilityInGame);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // En la diana.
    @GetMapping("/target/{cardId}")
    private String target(@PathVariable("cardId") Integer cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // Gana una ficha de gloria.
        Statistic statistics = currentPlayer.getStatistic();
        statistics.setGlory(statistics.getGlory() + 1);
        // Pierde una carta.
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        deck.getInDeck().remove(abilityInGame);
        deck.getInDiscard().add(abilityInGame);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Lluvia de flechas.
    @GetMapping("/arrowRain/{cardId}")
    private String arrowRain(@PathVariable("cardId") Integer cardId, HttpSession session, ModelMap model) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
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
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // Recupera una carta de diparo rápido de la pila de descarte.
        Deck deck = currentPlayer.getDeck();
        Optional<AbilityInGame> abilityInGame = deck.getInDiscard().stream().filter(a -> a.getAbility().getName().equals("Disparo rápido")).findFirst();
        if (abilityInGame.isPresent()) {
            deck.getInDiscard().remove(abilityInGame.get());
            deck.getInDeck().add(abilityInGame.get());
            // Baraja.
            Collections.shuffle(deck.getInDeck());

        }
        // Gana una moneda.
        Statistic statistics = currentPlayer.getStatistic();
        statistics.setGold(statistics.getGold() + 1);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Supervivencia
    @GetMapping("/survival/{cardId}")
    private String survival(@PathVariable("cardId") Integer cardId, HttpSession session, ModelMap model) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // Gana una carta de supervivencia.
        model.put("name", "getOutEnemy");
        return VIEW_CHOSE_ENEMY;
    }


}
