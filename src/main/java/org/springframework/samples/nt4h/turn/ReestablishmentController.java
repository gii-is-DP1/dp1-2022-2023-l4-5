package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/reestablishment")
public class ReestablishmentController {

    private final UserService userService;
    private final PlayerService playerService;
    private final DeckService deckService;
    private final GameService gameService;
    private final TurnService turnService;

    public final String VIEW_REESTABLISHMENT = "turns/reestablishmentPhase";
    private final String PAGE_REESTABLISHMENT = "redirect:/reestablishment/addCards";
    private final String NEXT_TURN = "redirect:/turns";
    private final Advise advise;

    @Autowired
    public ReestablishmentController(UserService userService, PlayerService playerService, DeckService deckService, GameService gameService, TurnService turnService) {
        this.playerService = playerService;
        this.userService = userService;
        this.deckService = deckService;
        this.gameService = gameService;
        this.turnService = turnService;
        this.advise = new Advise();
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        User loggedUser = getLoggedUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(Statistic.createStatistic()).build();
    }

    @ModelAttribute("currentPlayer")
    public Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("turn")
    public Turn getTurn() { return new Turn(); }

    @ModelAttribute("game")
    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("enemiesInBattle")
    public List<EnemyInGame> getEnemiesInBattle() {
        return getGame().getActualOrcs();
    }

    @ModelAttribute("allEnemiesInGame")
    public List<EnemyInGame> getAllEnemies() {
        return getGame().getAllOrcsInGame();
    }

    @ModelAttribute("chat")
    public Message getChat() {
        return new Message();
    }

    @GetMapping("/addCards")
    public String reestablishmentAddCards(HttpSession session, HttpServletRequest request) {
        playerService.restoreEnemyLife(getEnemiesInBattle());
        playerService.addNewEnemiesToBattle(getEnemiesInBattle(), getAllEnemies(), getGame());
        advise.keapUrl(session, request);
        return VIEW_REESTABLISHMENT;
    }

    @PostMapping("/addCards")
    public String takeNewAbility(Integer cardId) {
        Player player = getPlayer();
        Deck deck = player.getDeck();
        deckService.takeNewCard(player, deck);
        deckService.removeAbilityCards(cardId, player);
        return PAGE_REESTABLISHMENT;
    }

    @GetMapping("/next")
    public String reestablishmentNextTurn() throws NoCurrentPlayer {
        if (getLoggedPlayer() != getPlayer())
            throw new NoCurrentPlayer();
        int totalPlayers = getGame().getPlayers().size();
        Integer nextSequence = (getGame().getCurrentPlayer().getSequence()+1) % totalPlayers;
        Player nextPlayer = getGame().getPlayers().stream().filter(p -> p.getSequence() == nextSequence).findFirst().get();
        getGame().setCurrentPlayer(nextPlayer);
        getGame().setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.START, nextPlayer.getId()));
        gameService.saveGame(getGame());
        return NEXT_TURN;
    }

}
