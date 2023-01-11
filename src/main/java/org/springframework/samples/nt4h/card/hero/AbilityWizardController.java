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

    private final String PAGE_MAKE_DAMAGE = "redirect:/heroAttack/makeDamage";
    private final UserService userService;
    private final PlayerService playerService;
    private final StatisticService statisticService;
    private final CacheManager cacheManager;
    private final DeckService deckService;

    public AbilityWizardController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService, StatisticService statisticService, CacheManager cacheManager, DeckService deckService) {
        this.userService = userService;
        this.playerService = playerService;
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
    public String protectiveAura(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Deberían de ser efectos.
        // Anula el daño.
        cacheManager.hasPreventDamageFromEnemies(session);
        // Pierde una carta por cada enemigo.
        Deck deck = currentPlayer.getDeck();
        for (var i = 0; i < getGame().getActualOrcs().size(); i++) {
            deckService.loseACard(deck);
        }
        playerService.savePlayer(currentPlayer);
        return PAGE_MAKE_DAMAGE;
    }

    // Bola de fuego
    @GetMapping("/fireball/{cardId}")
    public String fireball(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
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
        return PAGE_MAKE_DAMAGE;
    }

    // Disparo gélido
    @GetMapping("/frostShot/{cardId}")
    public String frostShot(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Roba una carta.
        deckService.retrievesACard(currentPlayer.getDeck());
        // Elegimos el enemigo que no va a realizar daño.
        cacheManager.addPreventDamageFromEnemies(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Flecha corrosiva
    @GetMapping("/corrosiveArrow/{cardId}")
    public String corrosiveArrow(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Pierde una carta.
        deckService.loseACard(currentPlayer.getDeck());
        // Aumenta en 1 el daño hacia ese enemigo.
        cacheManager.addEnemiesThatReceiveMoreDamage(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Golpe de bastón
    @GetMapping("/staffHit/{cardId}")
    public String staffHit(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // SI ya ha sido atacado con golpe de bastón, realiza más daño.
        if (cacheManager.hasAlreadyAttackedWithStaff(session))
            cacheManager.addAttack(session, 1);
        else
            cacheManager.addAlreadyAttackedWithStaff(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Orbe curativo.
    @GetMapping("/healingOrb/{cardId}")
    public String healingOrb(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
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
        return PAGE_MAKE_DAMAGE;
    }

    // Proyectil ígneo
    @GetMapping("/igneousProjectile/{cardId}")
    public String igneousProjectile(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Gana 1 ficha de gloria.
        statisticService.gainGlory(currentPlayer.getStatistic(), 1);
        return PAGE_MAKE_DAMAGE;
    }

    // Reconstitución.
    @GetMapping("/reconstitution/{cardId}")
    public String reconstitution(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        Deck deck = currentPlayer.getDeck();
        // Roba 1 carta.
        deckService.retrievesACard(deck);
        // Recupera 2 cartas.
        for (var i = 0; i < 2; i++)
            deckService.retrievesACard(deck);
        return PAGE_MAKE_DAMAGE;
    }

    // Torrente de luz.
    @GetMapping("/lightTorrent/{cardId}")
    public String lightTorrent(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // El resto de héroes recuperan las cartas.
        List<Player> otherPlayers = getGame().getPlayers().stream().filter(p -> p != currentPlayer).collect(Collectors.toList());
        for (var i = 0; i < otherPlayers.size(); i++) {
            Player player = otherPlayers.get(i);
            deckService.retrievesACard(player.getDeck());
        }
        // Gana 1 ficha de gloria.
        statisticService.gainGlory(currentPlayer.getStatistic(), 1);
        return PAGE_MAKE_DAMAGE;
    }
}
