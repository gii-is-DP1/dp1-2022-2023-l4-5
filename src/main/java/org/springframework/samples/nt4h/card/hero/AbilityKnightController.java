package org.springframework.samples.nt4h.card.hero;

import com.google.common.collect.Lists;
import org.hibernate.stat.Statistics;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final String PAGE_LOSE_CARD = "redirect:/loseCard";
    private final String VIEW_LOSE_CARD = "abilities/loseCard";
    private final String VIEW_CHOSE_ENEMY = "abilities/choseEnemy";
    private final UserService userService;
    private final AbilityService abilityService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameService gameService;

    public AbilityKnightController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService) {
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

    // Ataque brutal
    @GetMapping("/brutalAttack/{cardId}")
    private String doBrutalAttack(@PathVariable("cardId") int cardId) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        return VIEW_LOSE_CARD;
    }

    // Carga con escudo.
    @GetMapping("/shieldCharge/{cardId}")
    private String doShieldCharge(@PathVariable("cardId") int cardId, HttpSession session) {
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

    // Doble espadazo.
    @GetMapping("/doubleSlash/{cardId}")
    private String doDoubleSlash(@PathVariable("cardId") int cardId) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // TODO: comprobar que el id es correcto.
        // Elimina una carta de la mano.
        AbilityInGame abilityInGame = abilityService.getAbilityInGameById(cardId);
        Deck deck = currentPlayer.getDeck();
        deck.discardCardOnHand(abilityInGame);
        playerService.savePlayer(currentPlayer);
        //
        return PAGE_HERO_ATTACK;
    }

    // Escudo.
    @GetMapping("/shield/{cardId}")
    private String doShield(@PathVariable("cardId") int cardId, HttpSession session, ModelMap model) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // TODO: comprobar que tiene esa carta.
        // Termina el turno.
        session.setAttribute("nextUrl", PAGE_HERO_ATTACK + "/next");
        model.put("name", "preventdamageFrom");
        return VIEW_CHOSE_ENEMY;
    }

    // Espadazo.
    @GetMapping("/slash/{cardId}")
    private String doSlash(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Comprueba si es el primer slash.
        if (session.getAttribute("firstSlash") == null) {
            session.setAttribute("firstSlash", true);
            Deck deck = currentPlayer.getDeck();
            // Probablemente sea un efecto.
            // Si lo es, roba una carta.
            AbilityInGame abilityInGame = deck.getInDeck().get(0);
            deck.getInDeck().remove(abilityInGame);
            deck.getInHand().add(abilityInGame);
            //
            playerService.savePlayer(currentPlayer);
        }
        return PAGE_HERO_ATTACK;
    }

    // Paso atrás.
    @GetMapping("/stepBack/{cardId}")
    private String doStepBack(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // Roba dos cartas.
        for (int i = 0; i < 2; i++) {
            Deck deck = currentPlayer.getDeck();
            AbilityInGame abilityInGame = deck.getInDeck().get(0);
            deck.getInDeck().remove(abilityInGame);
            deck.getInHand().add(abilityInGame);
        }
        //
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Todo o nada.
    @GetMapping("/allOrNothing/{cardId}")
    private String doAllOrNothing(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // Roba una carta.
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        deck.getInDeck().remove(abilityInGame);
        deck.getInHand().add(abilityInGame);
        playerService.savePlayer(currentPlayer);
        // Agrega ese daño a la carta.
        if (session.getAttribute("attack") == null)
            session.setAttribute("attack", 2);
        else
            session.setAttribute("attack", (int) session.getAttribute("attack") + 2);
        //
        return PAGE_HERO_ATTACK;
    }

    // Voz de aliento
    @GetMapping("/voiceOfEncouragement/{cardId}")
    private String doVoiceOfEncouragement(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        List<Player> players = getGame().getPlayers();
        // cada jugador roba dos cartas.
        for (Player player : players) {
            Deck deck = player.getDeck();
            AbilityInGame abilityInGame = deck.getInDiscard().get(0);
            deck.getInDiscard().remove(abilityInGame);
            deck.getInHand().add(abilityInGame);
            playerService.savePlayer(player);
        }
        // Roba una carta y gana una ficha de gloria.
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        deck.getInDeck().remove(abilityInGame);
        deck.getInHand().add(abilityInGame);
        Statistic statistics = currentPlayer.getStatistic();
        statistics.setGlory(statistics.getGlory() + 1);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    //////////////////////////////
    // Probablemente se mueva a otro controlador.
    //////////////////////////////




}
