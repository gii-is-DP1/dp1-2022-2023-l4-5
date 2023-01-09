package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
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
import org.springframework.samples.nt4h.turn.exceptions.TooManyAbilitiesException;
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
import java.util.Objects;

@Controller
@RequestMapping("/reestablishment")
public class ReestablishmentController {

    private final UserService userService;
    private final PlayerService playerService;
    private final DeckService deckService;
    private final GameService gameService;
    private final TurnService turnService;
    private final Advise advise;
    private final AbilityService abilityService;

    public final String VIEW_REESTABLISHMENT = "turns/reestablishmentPhase";
    private final String PAGE_REESTABLISHMENT = "redirect:/reestablishment";
    private final String NEXT_TURN = "redirect:/turns";
    private boolean hasAddedEnemies = false;


    @Autowired
    public ReestablishmentController(UserService userService, PlayerService playerService, DeckService deckService, GameService gameService, TurnService turnService, Advise advise, AbilityService abilityService) {
        this.playerService = playerService;
        this.userService = userService;
        this.deckService = deckService;
        this.gameService = gameService;
        this.turnService = turnService;
        this.advise = advise;
        this.abilityService = abilityService;
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

    @ModelAttribute("chat")
    public Message getChat() {
        return new Message();
    }


    @GetMapping
    public String reestablishmentAddCards(HttpSession session, HttpServletRequest request) throws NoCurrentPlayer {
        Game game = getGame();
        Player currentPlayer = getCurrentPlayer();
        advise.keepUrl(session, request);
        if ((getLoggedPlayer() == currentPlayer) && !hasAddedEnemies) {
            List<EnemyInGame> enemiesInBattle = game.getActualOrcs();
            gameService.restoreEnemyLife(enemiesInBattle);
            List<EnemyInGame> added = gameService.addNewEnemiesToBattle(enemiesInBattle, game.getAllOrcsInGame(), game);
            advise.addEnemies(added, game);
            hasAddedEnemies = true;
        }
        return VIEW_REESTABLISHMENT;
    }

    @PostMapping
    public String discardAbility(Turn turn) throws NoCurrentPlayer {
        Game game = getGame();
        Player currentPlayer = getCurrentPlayer();
        if (getLoggedPlayer() != currentPlayer)
            throw new NoCurrentPlayer();
        AbilityInGame currentAbility = turn.getCurrentAbility();
        Turn oldTurn = turnService.getTurnsByPhaseAndPlayerId(Phase.REESTABLISHMENT, currentPlayer.getId());
        oldTurn.addAbility(currentAbility);
        turnService.saveTurn(oldTurn);
        deckService.removeAbilityCards(currentAbility.getId(), currentPlayer);
        advise.discardAbilityInHand(currentAbility, game);
        return PAGE_REESTABLISHMENT;
    }

    @GetMapping("/next")
    public String reestablishmentNextTurn() throws TooManyAbilitiesException {
        Game game = getGame();
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer == currentPlayer) {
            if (currentPlayer.getDeck().getInHand().size() > 4)
                throw new TooManyAbilitiesException();
            List<Player> players = game.getPlayers();

            advise.addAbilityInHand(deckService.takeNewCard(currentPlayer), game);
            int totalPlayers = players.size();
            Integer nextSequence = (currentPlayer.getSequence()+1) % totalPlayers;
            Player nextPlayer = players.stream().filter(p -> Objects.equals(p.getSequence(), nextSequence)).findFirst().get();
            game.setCurrentPlayer(nextPlayer);
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.START, nextPlayer.getId()));
            gameService.saveGame(game);
            advise.changePlayer(loggedPlayer, game);
            hasAddedEnemies = false;
        }
        return NEXT_TURN;
    }

}
