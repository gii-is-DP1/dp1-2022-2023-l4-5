package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.card.product.exceptions.NotInSaleException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
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
import java.util.List;

@Controller
@RequestMapping("/market")
public class MarketController {
    public final String VIEW_MARKET = "turns/marketPhase";
    public final String PAGE_MARKET = "redirect:/market";
    public final String NEXT_TURN = "redirect:/turns";

    private final UserService userService;
    private final ProductService productService;
    private final TurnService turnService;
    private final GameService gameService;
    private final Advise advise;

    @Autowired
    public MarketController(UserService userService, ProductService productService, TurnService turnService, GameService gameService, Advise advise) {
        this.userService = userService;
        this.productService = productService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.advise = advise;
    }

    @ModelAttribute("productsOnSale")
    public List<ProductInGame> getProductsInSell() {
        return productService.getMarket();
    }

    @ModelAttribute("newTurn")
    public Turn getTurn() {

        return new Turn();
    }

    @ModelAttribute("game")
    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    @ModelAttribute("currentPlayer")
    public Player getPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        User loggedUser = getLoggedUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(Statistic.createStatistic()).build();
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("chat")
    public Message getChat() {
        return new Message();
    }

    @GetMapping
    public String market(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
        advise.getMessage(session, modelMap);
        advise.keepUrl(session, request);
        return VIEW_MARKET;
    }

    @PostMapping
    public String buyProduct(Turn turn) throws NoCurrentPlayer, NoMoneyException, NotInSaleException {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        productService.buyProduct(player, turn.getCurrentProduct());
        Turn oldTurn = turnService.getTurnsByPhaseAndPlayerId(Phase.MARKET, player.getId());
        oldTurn.addProduct(turn.getCurrentProduct());
        turnService.saveTurn(oldTurn);
        return PAGE_MARKET;
    }

    @GetMapping("/next")
    public String next() {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        Game game = getGame();
        if (loggedPlayer == player) {
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.REESTABLISHMENT, player.getId()));
            gameService.saveGame(game);
        }
        return NEXT_TURN;
    }
}
