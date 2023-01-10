package org.springframework.samples.nt4h.card.hero;

import com.google.common.collect.Lists;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.Statistic;
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

    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final String PAGE_LOSE_CARD = "redirect:/loseCard";
    private final String VIEW_LOSE_CARD = "abilities/loseCard";
    private final String VIEW_CHOSE_ENEMY = "abilities/choseEnemy";
    private final UserService userService;
    private final AbilityService abilityService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameService gameService;

    public AbilityThiefController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService) {
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

    // Al corazón.
    @GetMapping("/toTheHearth/{cardId}")
    private String toTheHearth(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Comprobamos si podemos cargarnos al enemigo.
        EnemyInGame attackedEnemy = (EnemyInGame) session.getAttribute("attackedEnemy");
        Integer attack = (Integer) session.getAttribute("attack");
        AbilityInGame abilityInGame = abilityService.getAbilityInGameById(cardId);
        if (abilityInGame.getAttack() + attack >= attackedEnemy.getActualHealth() && session.getAttribute("isToTheHearthPlayedInThisTurn") == null) {
            session.setAttribute("isToTheHearthPlayedInThisTurn", true);
            // Gana uno de oro.
            Statistic statistic = currentPlayer.getStatistic();
            statistic.setGold(statistic.getGold() + 1);
            // Pierde 1 carta.
            Deck deck = currentPlayer.getDeck();
            AbilityInGame abilityInGameToLose = deck.getInDeck().get(0);
            deck.getInDeck().remove(abilityInGameToLose);
            deck.getInDiscard().add(abilityInGameToLose);
            playerService.savePlayer(currentPlayer);
        }
        return PAGE_HERO_ATTACK;
    }

    // Ataque furtivo.
    @GetMapping("/stealthAttack/{cardId}")
    private String stealthAttack(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Comprobamos si podemos cargarnos al enemigo.
        EnemyInGame attackedEnemy = (EnemyInGame) session.getAttribute("attackedEnemy");
        Integer attack = (Integer) session.getAttribute("attack");
        AbilityInGame abilityInGame = abilityService.getAbilityInGameById(cardId);
        if (abilityInGame.getAttack() + attack >= attackedEnemy.getActualHealth() && session.getAttribute("isStealthAttackPlayedInThisTurn") == null) {
            session.setAttribute("isStealthAttackPlayedInThisTurn", true);
            // Gana uno de oro.
            Statistic statistic = currentPlayer.getStatistic();
            statistic.setGold(statistic.getGold() + 1);
            // Pierde 1 carta.
            Deck deck = currentPlayer.getDeck();
            AbilityInGame abilityInGameToLose = deck.getInDeck().get(0);
            deck.getInDeck().remove(abilityInGameToLose);
            deck.getInDiscard().add(abilityInGameToLose);
            playerService.savePlayer(currentPlayer);
        }
        return PAGE_HERO_ATTACK;
    }

    // Ballesta precisa.
    @GetMapping("/preciseBow/{cardId}")
    private String preciseBow(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // SI ya ha sido atacado con golpe de bastón, realiza más daño.
        EnemyInGame attackedEnemy = (EnemyInGame) session.getAttribute("attackedEnemy");
        List<Integer> alreadyAttackedWithStaff = (List<Integer>) session.getAttribute("alreadyAttackedWithBow");
        if (alreadyAttackedWithStaff == null) {
            session.setAttribute("alreadyAttackedWithBow", Lists.newArrayList(attackedEnemy.getId()));
        } else if (alreadyAttackedWithStaff.contains(attackedEnemy.getId())) {
            Integer attack = (Integer) session.getAttribute("attack");
            if (attack == null)
                session.setAttribute("attack", 1);
            else
                session.setAttribute("attack", attack + 1);
        } else
            alreadyAttackedWithStaff.add(attackedEnemy.getId());
        return PAGE_HERO_ATTACK;
    }

    // En las sombras.
    @GetMapping("/inTheShadows/{cardId}")
    private String inTheShadows(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Previene dos puntos de daño.
        if (session.getAttribute("defend") == null)
            session.setAttribute("defend", 2);
        else
            session.setAttribute("defend", (int) session.getAttribute("defend") + 2);
        return PAGE_HERO_ATTACK;
    }

    // Engañar
    @GetMapping("/deceive/{cardId}")
    private String deceive(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Elegimos el enemigo que no va a realizar daño.
        Statistic statistic = currentPlayer.getStatistic();
        if (statistic.getGold() >= 2) {
            statistic.setGold(statistic.getGold() - 2);
            playerService.savePlayer(currentPlayer);
            EnemyInGame enemyInGame = (EnemyInGame) session.getAttribute("attackedEnemy");
            session.setAttribute("preventdamageFrom", enemyInGame);
        }
        return PAGE_HERO_ATTACK;
    }

    // Robar bolsillos.
    @GetMapping("/stealPockets/{cardId}")
    private String stealPockets(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Roba una moneda a cada héroe.
        List<Player> otherPlayers = getGame().getPlayers().stream().filter(p -> p != currentPlayer).collect(Collectors.toList());
        Statistic currentPlayerStatistic = currentPlayer.getStatistic();
        for (Player otherPlayer : otherPlayers) {
            Statistic statistic = otherPlayer.getStatistic();
            if (statistic.getGold() > 0) {
                statistic.setGold(statistic.getGold() - 1);
                currentPlayerStatistic.setGold(currentPlayerStatistic.getGold() + 1);
                playerService.savePlayer(otherPlayer);
            }
        }
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Saqueo1
    @GetMapping("/loot1/{cardId}")
    private String loot1(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Ganas dos monedas por cada enemigo vivo.
        Statistic statistic = currentPlayer.getStatistic();
        statistic.setGold(statistic.getGold() + 2 * getGame().getActualOrcs().size());
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Saqueo2
    @GetMapping("/loot2/{cardId}")
    private String loot2(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Ganas una moneda por cada enemigo vivo.
        Statistic statistic = currentPlayer.getStatistic();
        statistic.setGold(statistic.getGold() + getGame().getActualOrcs().size());
        // Gana un punto de gloria.
        statistic.setGlory(statistic.getGlory() + 1);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Trampa
    @GetMapping("/trap/{cardId}")
    private String trap(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // El enemigo seleccionado morirá al terminar la fase de ataque.
        session.setAttribute("trap", session.getAttribute("attackedEnemy"));
        return PAGE_HERO_ATTACK;
    }
}
