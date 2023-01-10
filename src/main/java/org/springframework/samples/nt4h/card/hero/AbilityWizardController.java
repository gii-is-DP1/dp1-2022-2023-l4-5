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
import org.springframework.ui.ModelMap;
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

    public AbilityWizardController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService) {
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

    // Aura protectora
    @GetMapping("/protectiveAura/{cardId}")
    private String protectiveAura(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Deberían de ser efectos.
        // Anula el daño.
        session.setAttribute("noDamageFromEnemies", true);
        // Pierde una carta por cada enemigo.
        Deck deck = currentPlayer.getDeck();
        for (var i = 0; i < getGame().getActualOrcs().size(); i++) {
            AbilityInGame abilityInGame = deck.getInDeck().get(0);
            deck.getInDeck().remove(abilityInGame);
            deck.getInDiscard().add(abilityInGame);
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
        EnemyInGame attackedEnemy = (EnemyInGame) session.getAttribute("attackedEnemy");
        List<Integer> otherEnemies = game.getActualOrcs().stream().filter(enemy -> enemy != attackedEnemy).map(EnemyInGame::getId).collect(Collectors.toList());
        session.setAttribute("enemyAlsoAttacked", otherEnemies);
        // El resto de héroes sufre uno de daño.
        for (var i = 0; i < game.getPlayers().size(); i++) {
            Player player = game.getPlayers().get(i);
            if (player != currentPlayer) {
                Deck deck = player.getDeck();
                AbilityInGame abilityInGame = deck.getInDeck().get(0);
                deck.getInDeck().remove(abilityInGame);
                deck.getInDiscard().add(abilityInGame);
                playerService.savePlayer(player);
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
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        deck.getInDeck().remove(abilityInGame);
        deck.getInHand().add(abilityInGame);
        playerService.savePlayer(currentPlayer);
        // Elegimos el enemigo que no va a realizar daño.
        EnemyInGame enemyInGame = (EnemyInGame) session.getAttribute("attackedEnemy");
        session.setAttribute("preventdamageFrom", enemyInGame);
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
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        deck.getInDeck().remove(abilityInGame);
        deck.getInDiscard().add(abilityInGame);
        playerService.savePlayer(currentPlayer);
        // Aumenta en 1 el daño hacia ese enemigo.
        EnemyInGame dealMoreDamageTo = (EnemyInGame) session.getAttribute("attackedEnemy");
        session.setAttribute("dealMoreDamageTo", dealMoreDamageTo);
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
        EnemyInGame attackedEnemy = (EnemyInGame) session.getAttribute("attackedEnemy");
        List<Integer> alreadyAttackedWithStaff = (List<Integer>) session.getAttribute("alreadyAttackedWithStaff");
        if (alreadyAttackedWithStaff == null) {
            session.setAttribute("alreadyAttackedWithStaff", Lists.newArrayList(attackedEnemy.getId()));
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

    // Orbe curativo.
    @GetMapping("/healingOrb/{cardId}")
    private String healingOrb(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Todos los héroes recuperan dos cartas.
        for (var i = 0; i < getGame().getPlayers().size(); i++) {
            Player player = getGame().getPlayers().get(i);
            Deck deck = player.getDeck();
            AbilityInGame abilityInGame = deck.getInDiscard().get(0);
            deck.getInDiscard().remove(abilityInGame);
            deck.getInDeck().add(abilityInGame);
            playerService.savePlayer(player);
        }
        // Elimina 1 herida del héreo.
        Integer wounds = currentPlayer.getWounds();
        if (wounds > 0)
            currentPlayer.setWounds(wounds - 1);
        // Elimina la carta.
        session.setAttribute("deleteCard", true);
        playerService.savePlayer(currentPlayer);
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
        Statistic statistic = currentPlayer.getStatistic();
        statistic.setGlory(statistic.getGlory() + 1);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Reconstitución.
    @GetMapping("/reconstitution/{cardId}")
    private String reconstitution(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // Roba 1 carta.
        Deck deck = currentPlayer.getDeck();
        AbilityInGame abilityInGame = deck.getInDeck().get(0);
        deck.getInDeck().remove(abilityInGame);
        deck.getInHand().add(abilityInGame);
        // Recupera 2 cartas.
        for (var i = 0; i < 2; i++) {
            abilityInGame = deck.getInDiscard().get(0);
            deck.getInDiscard().remove(abilityInGame);
            deck.getInDeck().add(abilityInGame);
        }
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Torrente de luz.
    @GetMapping("/lightTorrent/{cardId}")
    private String lightTorrent(@PathVariable("cardId") int cardId, HttpSession session) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        // El resto de héreoes recuperan las cartas.
        List<Player> otherPlayers = getGame().getPlayers().stream().filter(p -> p != currentPlayer).collect(Collectors.toList());
        for (var i = 0; i < otherPlayers.size(); i++) {
            Player player = otherPlayers.get(i);
            Deck deck = player.getDeck();
            AbilityInGame abilityInGame = deck.getInDiscard().get(0);
            deck.getInDiscard().remove(abilityInGame);
            deck.getInDeck().add(abilityInGame);
            playerService.savePlayer(player);
        }
        // Gana 1 ficha de gloria.
        Statistic statistic = currentPlayer.getStatistic();
        statistic.setGlory(statistic.getGlory() + 1);
        return PAGE_HERO_ATTACK;
    }
}
