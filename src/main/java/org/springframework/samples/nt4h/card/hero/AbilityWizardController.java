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
 * - Bola de fuego. Fufa
 * - Disparo gélido. Fufa
 * - Flecha corrosiva. Fufa
 * - Golpe de bastón. Fufa
 * - Orbe curativo. Fufa
 * - Proyectil ígneo. Fufa
 * - Reconstitución. Fufa
 * - Torrente de luz. Fufa
 */
@Controller
@RequestMapping("/abilities")
public class AbilityWizardController {

    private final String PAGE_MAKE_DAMAGE = "redirect:/heroAttack/makeDamage";
    private final UserService userService;
    private final PlayerService playerService;
    private final StatisticService statisticService;
    private final CacheManager cacheManager;
    private final DeckService deckService;

    public AbilityWizardController(UserService userService, PlayerService playerService, StatisticService statisticService, CacheManager cacheManager, DeckService deckService) {
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
    @GetMapping("/protectiveAura")
    private String protectiveAura(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Anula el daño.
        cacheManager.addAllInBattlePreventDamageFromEnemies(session, getGame());
        // Pierde una carta por cada enemigo.
        Deck deck = currentPlayer.getDeck();
        for (var i = 0; i < getGame().getActualOrcs().size(); i++) {
            deckService.fromDeckToDiscard(currentPlayer, deck);
        }
        playerService.savePlayer(currentPlayer);
        return PAGE_MAKE_DAMAGE;
    }

    // Bola de fuego
    @GetMapping("/fireball")
    private String fireball(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Game game = getGame();
        // Atacamos a todos los enemigos
        cacheManager.addAllEnemiesAlsoAttacked(session, currentPlayer.getGame());
        // El resto de héroes sufre uno de daño.
        for (var i = 0; i < game.getPlayers().size(); i++) {
            Player player = game.getPlayers().get(i);
            if (player != currentPlayer) {
                deckService.fromDeckToDiscard(currentPlayer, player.getDeck());
            }
        }
        return PAGE_MAKE_DAMAGE;
    }

    // Disparo gélido
    @GetMapping("/frostShot")
    private String frostShot(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Roba una carta.
        deckService.fromDeckToHand(currentPlayer, currentPlayer.getDeck());
        // Elegimos el enemigo que no va a realizar daño.
        cacheManager.addPreventDamageFromEnemies(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Flecha corrosiva
    @GetMapping("/corrosiveArrow")
    private String corrosiveArrow(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Pierde una carta.
        deckService.fromDeckToDiscard(currentPlayer, currentPlayer.getDeck());
        // Aumenta en 1 el daño hacia ese enemigo.
        cacheManager.addEnemiesThatReceiveMoreDamage(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Golpe de bastón
    @GetMapping("/staffHit")
    private String staffHit(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // SI ya ha sido atacado con golpe de bastón, realiza más daño.
        if (cacheManager.hasAlreadyAttackedWithStaff(session))
            cacheManager.addAttack(session, 1);
        else
            cacheManager.addAlreadyAttackedWithStaff(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Orbe curativo.
    @GetMapping("/healingOrb")
    private String healingOrb(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        List<Player> players = getGame().getPlayers();
        // Todos los héroes recuperan dos cartas.
        for (var i = 0; i < players.size(); i++)
            deckService.fromDiscardToHand(players.get(i).getDeck(), 2);
        // Elimina 1 herida del héroe.
        playerService.decreaseWounds(currentPlayer, 1);
        // Elimina la carta.
        cacheManager.setHasToBeDeletedAbility(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Proyectil ígneo
    @GetMapping("/igneousProjectile")
    private String igneousProjectile() {
        Player currentPlayer = getCurrentPlayer();
        // Gana 1 ficha de gloria.
        statisticService.gainGlory(currentPlayer, 1);
        return PAGE_MAKE_DAMAGE;
    }

    // Reconstitución.
    @GetMapping("/reconstitution")
    private String reconstitution() {
        Player currentPlayer = getCurrentPlayer();
        Deck deck = currentPlayer.getDeck();
        // Roba 1 carta.
        deckService.fromDeckToHand(currentPlayer, deck);
        // Recupera 2 cartas.
        deckService.fromDiscardToHand(deck, 2);
        return PAGE_MAKE_DAMAGE;
    }

    // Torrente de luz.
    @GetMapping("/lightTorrent")
    private String lightTorrent() {
        Player currentPlayer = getCurrentPlayer();
        // El resto de héroes recuperan las cartas.
        List<Player> otherPlayers = getGame().getPlayers().stream().filter(p -> p != currentPlayer).collect(Collectors.toList());
        for (var i = 0; i < otherPlayers.size(); i++) {
            Player player = otherPlayers.get(i);
            deckService.fromDiscardToHand(player.getDeck());
        }
        // Gana 1 ficha de gloria.
        statisticService.gainGlory(currentPlayer, 1);
        return PAGE_MAKE_DAMAGE;
    }
}
