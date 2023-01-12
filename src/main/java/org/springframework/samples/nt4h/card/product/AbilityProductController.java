package org.springframework.samples.nt4h.card.product;

import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.capacity.StateCapacity;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.Turn;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Las habilidades de los productos son:
 * - Daga élfica.
 * - Poción curativa.
 * - Piedra amolar.
 * - Vial de conjuración.
 * - Elixir de concentración.
 * - Capa élfica.
 * - Armadura de placas.
 * - Alabarda orca.
 * - Arco compuesto.
 */
@Controller
@RequestMapping("/abilities")
public class AbilityProductController {

    private final String PAGE_MAKE_DAMAGE = "redirect:/heroAttack/makeDamage";
    private final String VIEW_FIND_IN_DISCARD = "abilities/findInDiscard";
    private final UserService userService;
    private final PlayerService playerService;
    private final CacheManager cacheManager;
    private final DeckService deckService;


    public AbilityProductController(UserService userService, PlayerService playerService, EnemyService enemyService, CacheManager cacheManager, DeckService deckService) {
        this.userService = userService;
        this.playerService = playerService;
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

    @ModelAttribute("logggedPlayer")
    public Player getLoggedPlayer() {
        return getLoggedUser().getPlayer();
    }

    //  Daga élfica
    @GetMapping("/elfDagger")
    private String elfDagger(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Pierde la carta si no tiene el héreo pericia.
        List<StateCapacity > stateCapacities = currentPlayer.getHeroes().stream().flatMap(hero -> hero.getHero()
            .getCapacities().stream().map(Capacity::getStateCapacity)).collect(Collectors.toList());
        if (!stateCapacities.contains(StateCapacity.EXPERTISE))
            cacheManager.setHasToBeDeletedAbility(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Poción curativa
    @GetMapping("/healingPotion")
    private String healingPotion(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Elimina una herida.
        playerService.decreaseWounds(currentPlayer, 1);
        // Eliminamos la carta.
        cacheManager.setHasToBeDeletedAbility(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Piedra amolar
    @GetMapping("/sharpeningStone")
    private String sharpeningStone(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Añadimos el daño por piedra amolar.
        cacheManager.setSharpeningStone(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Vial de conjuración
    @GetMapping("/conjureVial")
    private String conjureVial(ModelMap model) {
        Player currentPlayer = getCurrentPlayer();
        model.put("discard", currentPlayer.getDeck().getInDiscard());
        model.put("newTurn", new Turn());
        return VIEW_FIND_IN_DISCARD;
    }

    // Elixir de concentración.
    @GetMapping("/concentrationElixir")
    private String concentrationVial() {
        Player currentPlayer = getCurrentPlayer();
        // Roba 3 cartas.
        Deck deck = currentPlayer.getDeck();
        for (var i = 0; i < 3; i++)
            deckService.saveDeck(deck);
        return PAGE_MAKE_DAMAGE;
    }

    // Capa élfica
    @GetMapping("/elfCloak")
    private String elfCloak(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // El enemigo seleccionado no causa daño este turno.
        cacheManager.addPreventDamageFromEnemies(session);
        return PAGE_MAKE_DAMAGE;
    }

    // Armadura de placas.
    @GetMapping("/plateArmor")
    private String plateArmor() {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        // Recupera 4 cartas.
        Deck deck = currentPlayer.getDeck();
        for (var i = 0; i < 4; i++)
            deckService.fromDiscardToDeck(deck);
        return PAGE_MAKE_DAMAGE;
    }

    // Alabarda orca
    @GetMapping("/orcaLance")
    private String orcLance() {
        return PAGE_MAKE_DAMAGE;
    }

    // Arco compuesto.
    @GetMapping("/compoundBow")
    private String compoundBound() {
        return PAGE_MAKE_DAMAGE;
    }
}
