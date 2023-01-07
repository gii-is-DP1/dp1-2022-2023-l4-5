package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/heroAttack")
public class HeroAttackController {

    private final String NEXT_TURN = "redirect:/turns";
    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final String VIEW_HERO_ATTACK = "turns/attackPhase";

    private final UserService userService;
    private final TurnService turnService;
    private final GameService gameService;
    private final Advise advise;


    @Autowired
    public HeroAttackController(UserService userService, TurnService turnService, GameService gameService, Advise advise) {
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.advise = advise;
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        return userService.getLoggedUser().getPlayer();
    }

    @ModelAttribute("game")
    public Game getGame() {
        return getPlayer().getGame();
    }

    @ModelAttribute("newTurn")
    public Turn getNewTurn() {
        return new Turn();
    }

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        User loggedUser = getLoggedUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(Statistic.createStatistic()).build();
    }

    @ModelAttribute("chat")
    public Message getChat() {
        return new Message();
    }

    @GetMapping
    public String showHeroAttackBoard(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
        advise.getMessage(session, modelMap);
        advise.keapUrl(session, request);
        return VIEW_HERO_ATTACK;
    }


    @PostMapping
    public String modifyCardAttributes(Turn turn) throws NoCurrentPlayer {
        Player player = getPlayer();
        Game game = getGame();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        // TODO: debe de lanzar una excepción si no es el jugador loguado y mover a un servicio.
        if (player == getGame().getCurrentPlayer()) {
            AbilityInGame usedAbility = turn.getCurrentAbility();
            EnemyInGame attackedEnemy = turn.getCurrentEnemy();
            Integer enemyInitialHealth = attackedEnemy.getActualHealth();
            attackedEnemy.setActualHealth(enemyInitialHealth - usedAbility.getAttack());
            player.getDeck().getInHand().remove(usedAbility);
            player.getDeck().getInDiscard().add(usedAbility);
            //playerService.savePlayer(player);
            if (attackedEnemy.getActualHealth() <= 0) {
                player.getStatistic().setGlory(player.getStatistic().getGlory() + attackedEnemy.getEnemy().getGlory());
                player.getStatistic().setGold(player.getStatistic().getGold() + attackedEnemy.getEnemy().getGold());
                game.getActualOrcs().remove(attackedEnemy);
                //playerService.savePlayer(player);
                //gameService.saveGame(game);
            }
            Turn createdTurn = turnService.getTurnsByPhaseAndPlayerId(Phase.HERO_ATTACK, player.getId());
            createdTurn.addEnemy(attackedEnemy);
            createdTurn.addAbility(usedAbility);
            turnService.saveTurn(createdTurn);
        }
        return PAGE_HERO_ATTACK;
    }

    @GetMapping("/next")
    public String next() {
        Player player = getPlayer();
        Game game = getGame();
        if (player == getGame().getCurrentPlayer()) {
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.ENEMY_ATTACK, player.getId()));
            gameService.saveGame(game);
        }
        return NEXT_TURN;
    }

}
