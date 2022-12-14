package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.turn.exceptions.WithOutAbilityException;
import org.springframework.samples.nt4h.turn.exceptions.WithOutEnemyException;
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
    private final String PAGE_ABILITY = "redirect:/abilities";

    private final UserService userService;
    private final TurnService turnService;
    private final GameService gameService;
    private final PlayerService playerService;
    private final Advise advise;
    private final CacheManager cacheManager;
    private final DeckService deckService;

    @Autowired
    public HeroAttackController(UserService userService, TurnService turnService, GameService gameService, PlayerService playerService, Advise advise, CacheManager cacheManager, DeckService deckService) {
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.playerService = playerService;
        this.advise = advise;
        this.cacheManager = cacheManager;
        this.deckService = deckService;
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("currentPlayer")
    public Player getPlayer() {
        return getLoggedUser().getGame().getCurrentPlayer();
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
        advise.keepUrl(session, request);
        return VIEW_HERO_ATTACK;
    }

    @PostMapping
    public String modifyCardAttributes(Turn turn, HttpSession session) throws NoCurrentPlayer, WithOutAbilityException, WithOutEnemyException {
        Player player = getPlayer();
        Game game = getGame();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        Deck deck = loggedPlayer.getDeck();
        AbilityInGame usedAbility = turn.getCurrentAbility();
        EnemyInGame attackedEnemy = turn.getCurrentEnemy();
        if (usedAbility == null)
            throw new WithOutAbilityException();
        if (attackedEnemy == null)
            throw new WithOutEnemyException();
        Turn createdTurn = turnService.getTurnsByPhaseAndPlayerId(Phase.HERO_ATTACK, player.getId());
        playerService.savePlayer(player);
        createdTurn.addEnemy(attackedEnemy);
        createdTurn.addAbility(usedAbility);
        turnService.saveTurn(createdTurn);
        advise.heroAttack(usedAbility, attackedEnemy, game);
        return PAGE_ABILITY;
    }

    @GetMapping("/makeDamage")
    public String attackEnemy(Turn turn, HttpSession session) throws NoCurrentPlayer, WithOutAbilityException, WithOutEnemyException {
        Player player = getPlayer();
        Game game = getGame();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        Deck deck = loggedPlayer.getDeck();
        AbilityInGame usedAbility = turn.getCurrentAbility();
        EnemyInGame attackedEnemy = turn.getCurrentEnemy();
        if (usedAbility == null)
            throw new WithOutAbilityException();
        if (attackedEnemy == null)
            throw new WithOutEnemyException();
        deckService.attackEnemies(usedAbility, session, player, game, getLoggedUser().getId());
        deckService.specificCardFromHandToDiscard(deck, usedAbility);
        return PAGE_HERO_ATTACK;
    }

    @GetMapping("/next")
    public String next() {
        Player player = getPlayer();
        Game game = getGame();
        if (player == getGame().getCurrentPlayer()) {
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(((game.getActualOrcs().isEmpty()) && (game.getAllOrcsInGame().isEmpty())) ? Phase.END: Phase.ENEMY_ATTACK, player.getId()));
            gameService.saveGame(game);
            advise.passPhase(game);
        }
        return NEXT_TURN;
    }

}
