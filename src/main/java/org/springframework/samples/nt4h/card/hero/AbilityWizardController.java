package org.springframework.samples.nt4h.card.hero;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Las habilidades del mago son:
 * - Aura protectora.
 * - Bola de fuego.
 * - Disparo gélido.
 * - Flecha corrosiva.
 * - Golpe de bastón.
 * - Orbe curativo.
 * - Proyectil ígneo.
 * - Reconstitución.
 * - Torrente de luz.
 */
@Controller
@RequestMapping("/abilities/wizard")
public class AbilityWizardController {

    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final String PAGE_LOSE_CARD = "redirect:/loseCard";
    private final String VIEW_LOSE_CARD = "abilities/loseCard";
    private final String VIEW_CHOSE_ENEMY = "abilities/choseEnemy";
    private final UserService userService;
    private final AbilityService abilityService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameService gameService;
    private final StatisticService statisticService;
    private final CacheManager cacheManager;
    private final DeckService deckService;

    public AbilityWizardController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService, StatisticService statisticService, CacheManager cacheManager, DeckService deckService) {
        this.userService = userService;
        this.abilityService = abilityService;
        this.playerService = playerService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.statisticService = statisticService;
        this.cacheManager = cacheManager;
        this.deckService = deckService;
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

    // Aura protectora
    @GetMapping("/protectiveAura/{cardId}")
    private String protectiveAura(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Deberían de ser efectos.
        // Anula el daño.
        cacheManager.hasPreventDamageFromEnemies(session);
        // Pierde una carta por cada enemigo.
        Deck deck = currentPlayer.getDeck();
        for (var i = 0; i < getGame().getActualOrcs().size(); i++) {
            deckService.loseACard(deck);
        }
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Bola de fuego
    @GetMapping("/fireball/{cardId}")
    private String fireball(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        Game game = getGame();
        // Atacamos a todos los enemigos
        cacheManager.addAllEnemiesAlsoAttacked(session, currentPlayer.getGame());
        // El resto de héroes sufre uno de daño.
        for (var i = 0; i < game.getPlayers().size(); i++) {
            Player player = game.getPlayers().get(i);
            if (player != currentPlayer) {
                deckService.loseACard(player.getDeck());
            }
        }
        return PAGE_HERO_ATTACK;
    }

    // Disparo gélido
    @GetMapping("/frostShot/{cardId}")
    private String frostShot(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Roba una carta.
        deckService.retrievesACard(currentPlayer.getDeck());
        // Elegimos el enemigo que no va a realizar daño.
        cacheManager.addPreventDamageFromEnemies(session);
        return PAGE_HERO_ATTACK;
    }

    // Flecha corrosiva
    @GetMapping("/corrosiveArrow/{cardId}")
    private String corrosiveArrow(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Pierde una carta.
        deckService.loseACard(currentPlayer.getDeck());
        // Aumenta en 1 el daño hacia ese enemigo.
        cacheManager.addEnemiesThatReceiveMoreDamage(session);
        return PAGE_HERO_ATTACK;
    }

    // Golpe de bastón
    @GetMapping("/staffHit/{cardId}")
    private String staffHit(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // SI ya ha sido atacado con golpe de bastón, realiza más daño.
        if (cacheManager.hasAlreadyAttackedWithStaff(session))
            cacheManager.addAttack(session, 1);
        else
            cacheManager.addAlreadyAttackedWithStaff(session);
        return PAGE_HERO_ATTACK;
    }

    // Orbe curativo.
    @GetMapping("/healingOrb/{cardId}")
    private String healingOrb(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Todos los héroes recuperan dos cartas.
        for (var i = 0; i < getGame().getPlayers().size(); i++) {
            for (var j = 0; j < 2; j++) {
                deckService.retrievesACard(getGame().getPlayers().get(i).getDeck());
            }
        }
        // Elimina 1 herida del héroe.
        playerService.decreaseWounds(currentPlayer, 1);
        // Elimina la carta.
        cacheManager.setHasToBeDeletedAbility(session);
        return PAGE_HERO_ATTACK;
    }

    // Proyectil ígneo
    @GetMapping("/igneousProjectile/{cardId}")
    private String igneousProjectile(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Gana 1 ficha de gloria.
        statisticService.gainGlory(currentPlayer.getStatistic(), 1);
        return PAGE_HERO_ATTACK;
    }

    // Reconstitución.
    @GetMapping("/reconstitution/{cardId}")
    private String reconstitution(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        Deck deck = currentPlayer.getDeck();
        // Roba 1 carta.
        deckService.retrievesACard(deck);
        // Recupera 2 cartas.
        for (var i = 0; i < 2; i++)
            deckService.retrievesACard(deck);
        return PAGE_HERO_ATTACK;
    }

    // Torrente de luz.
    @GetMapping("/lightTorrent/{cardId}")
    private String lightTorrent(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // El resto de héroes recuperan las cartas.
        List<Player> otherPlayers = getGame().getPlayers().stream().filter(p -> p != currentPlayer).collect(Collectors.toList());
        for (var i = 0; i < otherPlayers.size(); i++) {
            Player player = otherPlayers.get(i);
            deckService.retrievesACard(player.getDeck());
        }
        // Gana 1 ficha de gloria.
        statisticService.gainGlory(currentPlayer.getStatistic(), 1);
        return PAGE_HERO_ATTACK;
    }
}
