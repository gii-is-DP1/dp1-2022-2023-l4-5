package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.function.Predicate;

@Controller
@RequestMapping("/enemyAttack")
public class EnemyAttackController {


    private final UserService userService;
    private final TurnService turnService;
    private final GameService gameService;
    private final CacheManager cacheManager;
    private final EnemyService enemyService;
    public final String VIEW_ATTACK = "turns/attackPhase";
    public final String NEXT_TURN = "redirect:/turns";
    private final Advise advise;

    private Integer damage;

    @ModelAttribute("loggedUser")
    public User getUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("currentPlayer")
    public Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        User loggedUser = getUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(Statistic.createStatistic()).build();
    }

    @ModelAttribute("game")
    public Game getGame() {
        return getUser().getGame();
    }

    @ModelAttribute("newTurn")
    public Turn getNewTurn() {
        return new Turn();
    }

    @ModelAttribute("chat")
    public Message getChat() {
        return new Message();
    }



    @Autowired
    public EnemyAttackController(UserService userService, TurnService turnService, GameService gameService, CacheManager cacheManager, EnemyService enemyService, Advise advise) {
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.cacheManager = cacheManager;
        this.enemyService = enemyService;
        this.advise = advise;
        this.damage = null;
    }

    @GetMapping
    public String getEnemyAttack(ModelMap model, HttpSession session, HttpServletRequest request) {
        Game game = getGame();
        if (getCurrentPlayer() == getLoggedPlayer() && damage == null) {
            int defendedDmg = cacheManager.getDefend(session);
            System.out.println("Defended dmg: " + defendedDmg);
            Predicate<EnemyInGame> hasPreventedDamage = enemy -> !(cacheManager.hasPreventDamageFromEnemies(session, enemy));
            List<EnemyInGame> enemiesInATrap = cacheManager.getCapturedEnemies(session);
            damage = gameService.attackEnemyToActualPlayer(game, session, hasPreventedDamage, defendedDmg, enemiesInATrap);
            advise.playerIsAttacked(damage);
        }
        model.put("damage", damage);
        advise.keepUrl(session, request);
        return VIEW_ATTACK;
    }

    @GetMapping("/next")
    public String nextTurn(HttpSession session) {
        cacheManager.deleteEndAttackEnemy(session);
        cacheManager.deleteEndAttackHero(session);
        Player player = getLoggedPlayer();
        Game game = getGame();
        if(player == game.getCurrentPlayer()) {
            damage = null;
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.MARKET, player.getId()));
            gameService.saveGame(game);
            advise.passPhase(game);
        }
        return NEXT_TURN;
    }
}
