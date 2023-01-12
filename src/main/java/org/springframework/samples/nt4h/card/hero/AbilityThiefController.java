package org.springframework.samples.nt4h.card.hero;

import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.Statistic;
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

/**
 * Las habilidades del ladrón son:
 * - Al corazón.
 * - Ataque furtivo.
 * - Ballesta precisa.
 * - En las sombras.
 * - Engañar.
 * - Robar bolsillos.
 * - Saqueo1.
 * - Saque2.
 * - Trampa.
 */
@Controller
@RequestMapping("/abilityies/thief")
public class AbilityThiefController {

    private final String PAGE_MAKE_DAMAGE = "redirect:/heroAttack/makeDamage";
    private final UserService userService;
    private final AbilityService abilityService;
    private final CacheManager cacheManager;
    private final StatisticService statisticService;
    private final DeckService deckService;

    public AbilityThiefController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService, EnemyService enemyService, CacheManager cacheManager, StatisticService statisticService, DeckService deckService) {
        this.userService = userService;
        this.abilityService = abilityService;
        this.cacheManager = cacheManager;
        this.statisticService = statisticService;
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

    // Al corazón.
    @GetMapping("/toTheHearth")
    private String toTheHearth(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Comprobamos si podemos cargarnos al enemigo.
        EnemyInGame attackedEnemy = cacheManager.getAttackedEnemy(session);

        Integer sharpeningStone = cacheManager.getSharpeningStone(session);
        Integer extraDamage = cacheManager.getEnemiesThatReceiveMoreDamageForEnemy(session, attackedEnemy);
        Ability ability = abilityService.getAbilityByName("Al Corazón");
        int attack = cacheManager.getAttack(session) + ability.getAttack() + sharpeningStone + extraDamage;
        if (attack >= attackedEnemy.getActualHealth() && cacheManager.isFirstToTheHearth(session)) {
            cacheManager.setFirstToTheHearth(session);
            // Gana uno de oro.
            statisticService.gainGlory(currentPlayer, 1);
        }
        // Pierde 1 carta.
        deckService.fromDeckToDiscard(currentPlayer, currentPlayer.getDeck());
        return PAGE_MAKE_DAMAGE;
    }

    // Ataque furtivo.
    @GetMapping("/stealthAttack")
    private String stealthAttack(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Comprobamos si podemos cargarnos al enemigo.
        EnemyInGame attackedEnemy = cacheManager.getAttackedEnemy(session);
        Integer sharpeningStone = cacheManager.getSharpeningStone(session);
        Integer extraDamage = cacheManager.getEnemiesThatReceiveMoreDamageForEnemy(session, attackedEnemy);
        Ability ability = abilityService.getAbilityByName("Ataque Furtivo");
        int attack = cacheManager.getAttack(session) + ability.getAttack() + sharpeningStone + extraDamage;
        if (attack >= attackedEnemy.getActualHealth() && cacheManager.isFirstStealthAttack(session)) {
            cacheManager.setFirstStealthAttack(session);
            // Gana uno de oro.
            statisticService.gainGold(currentPlayer, 1);
        }
        return PAGE_MAKE_DAMAGE;
    }

    // Ballesta precisa.
    @GetMapping("/preciseBow")
    private String preciseBow(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // SI ya ha sido atacado con golpe de bastón, realiza más daño.
        if (cacheManager.hasAlreadyAttackedWithPreciseBow(session))
            cacheManager.addAttack(session,  1);
        else
            cacheManager.addAlreadyAttackedWithPreciseBow(session);
        return PAGE_MAKE_DAMAGE;
    }

    // En las sombras.
    @GetMapping("/inTheShadows")
    private String inTheShadows(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Previene dos puntos de daño.
        cacheManager.setDefend(session, 2);
        return PAGE_MAKE_DAMAGE;
    }

    // Engañar
    @GetMapping("/deceive")
    private String deceive(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // Elegimos el enemigo que no va a realizar daño.
        Statistic statistic = currentPlayer.getStatistic();
        if (statistic.getGold() >= 2) {
            statisticService.loseGold(statistic, 2);
            cacheManager.addPreventDamageFromEnemies(session);
        }
        return PAGE_MAKE_DAMAGE;
    }

    // Robar bolsillos.
    @GetMapping("/stealPockets")
    private String stealPockets() {
        Player currentPlayer = getCurrentPlayer();
        // Roba una moneda a cada héroe.
        for (Player player : getGame().getPlayers()) {
            Statistic statistic = player.getStatistic();
            if (statistic.getGold() > 0 && player != currentPlayer) {
                statisticService.loseGold(statistic, 1);
                statisticService.gainGold(currentPlayer, 1);
            }
        }
        return PAGE_MAKE_DAMAGE;
    }

    // Saqueo1
    @GetMapping("/loot1/{cardId}")
    private String loot1() {
        Player currentPlayer = getCurrentPlayer();
        // Ganas dos monedas por cada enemigo vivo.
        statisticService.gainGold(currentPlayer, 2 * getGame().getActualOrcs().size());
        return PAGE_MAKE_DAMAGE;
    }

    // Saqueo2
    @GetMapping("/loot2")
    private String loot2() {
        Player currentPlayer = getCurrentPlayer();
        // Ganas una moneda por cada enemigo vivo.
        statisticService.gainGold(currentPlayer, getGame().getActualOrcs().size());
        // Gana un punto de gloria.
        statisticService.gainGlory(currentPlayer, 1);
        return PAGE_MAKE_DAMAGE;
    }

    // Trampa
    @GetMapping("/trap")
    private String trap(HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        // El enemigo seleccionado morirá al terminar la fase de ataque.
        cacheManager.addCapturedEnemies(session);
        return PAGE_MAKE_DAMAGE;
    }
}
