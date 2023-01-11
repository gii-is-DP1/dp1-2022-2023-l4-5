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

    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final String PAGE_LOSE_CARD = "redirect:/loseCard";
    private final String VIEW_LOSE_CARD = "abilities/loseCard";
    private final String VIEW_CHOSE_ENEMY = "abilities/choseEnemy";
    private final UserService userService;
    private final AbilityService abilityService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameService gameService;
    private final DeckService deckService;
    private final CacheManager cacheManager;
    private final StatisticService statisticService;

    public AbilityKnightController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService, DeckService deckService, CacheManager cacheManager, StatisticService statisticService) {
        this.userService = userService;
        this.abilityService = abilityService;
        this.playerService = playerService;
        this.turnService = turnService;
        this.gameService = gameService;
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
            return PAGE_HERO_ATTACK;
        // Pierde una carta.
        deckService.loseACard(currentPlayer.getDeck());
        return VIEW_LOSE_CARD;
    }

    // Carga con escudo.
    @GetMapping("/shieldCharge/{cardId}")
    private String shieldCharge(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Aumenta el valor de la defensa en dos.
        cacheManager.setDefend(session, 2);
        return PAGE_HERO_ATTACK;
    }

    // Doble espadazo.
    @GetMapping("/doubleSlash/{cardId}")
    private String doubleSlash(@PathVariable("cardId") int cardId) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Elimina una carta de la mano.
        deckService.loseACard(currentPlayer.getDeck());
        return PAGE_HERO_ATTACK;
    }

    // Escudo.
    @GetMapping("/shield/{cardId}")
    private String shield(@PathVariable("cardId") int cardId, HttpSession session, ModelMap model) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Termina el turno.
        cacheManager.setNextUrl(session, PAGE_HERO_ATTACK + "/next");
        model.put("name", "preventDamageFrom");
        return VIEW_CHOSE_ENEMY;
    }

    // Espadazo.
    @GetMapping("/slash/{cardId}")
    private String slash(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Comprueba si es el primer slash.
        if (cacheManager.isFirstSlash(session)) {
            cacheManager.setFirstSlash(session);
            // Si lo es, roba una carta.
            deckService.retrievesACard(currentPlayer.getDeck());
        }
        return PAGE_HERO_ATTACK;
    }

    // Paso atrás.
    @GetMapping("/stepBack/{cardId}")
    private String stepBack(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Roba dos cartas.
        for (int i = 0; i < 2; i++) {
            deckService.retrievesACard(currentPlayer.getDeck());
        }
        return PAGE_HERO_ATTACK;
    }

    // Todo o nada.
    @GetMapping("/allOrNothing/{cardId}")
    private String allOrNothing(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Debería de ser un efecto
        // Roba una carta.
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        deckService.retrievesTheCard(deck, abilityInGame);
        // Agrega ese daño a la carta.
        cacheManager.addAttack(session, abilityInGame.getAttack());
        return PAGE_HERO_ATTACK;
    }

    // Voz de aliento
    @GetMapping("/voiceOfEncouragement/{cardId}")
    private String voiceOfEncouragement(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // cada jugador roba dos cartas.
        for (Player player : getGame().getPlayers()) {
            for (int i = 0; i < 2; i++) {
                deckService.retrievesACard(player.getDeck());
            }
        }
        // Roba una carta.
        deckService.retrievesACard(currentPlayer.getDeck());
        // Gana una ficha de gloria.
        statisticService.gainGlory(currentPlayer.getStatistic(), 1);
        return PAGE_HERO_ATTACK;
    }
}
