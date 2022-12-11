package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.product.Product;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/market")
public class MarketController {
    public final String VIEW_MARKET = "turns/marketPhase";
    public final String NEXT_TURN = "redirect:/turns";

    private final UserService userService;
    private final ProductService productService;
    private final TurnService turnService;
    private final GameService gameService;

    private final Advise advise;

    @Autowired
    public MarketController(UserService userService, ProductService productService, TurnService turnService, GameService gameService) {
        this.userService = userService;
        this.productService = productService;
        this.turnService = turnService;
        this.gameService = gameService;
        this.advise = new Advise();
    }

    @ModelAttribute("productsOnSale")
    public List<Product> getProductsInSell() {
        return productService.getMarket();
    }

    @ModelAttribute("productInGame")
    public ProductInGame getProductInGame() {
        return new ProductInGame();
    }

    @ModelAttribute("game")
    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("loggedPLayer")
    public Player getLoggedPlayer() {
        return userService.getLoggedUser().getPlayer();
    }

    @ModelAttribute("message")
    public String getMessage() {
        return advise.getMessage();
    }

    @ModelAttribute("messageType")
    public String getMessageType() {
        return advise.getMessageType();
    }

    @GetMapping
    public String market() {
        return VIEW_MARKET;
    }

    @PostMapping
    public String buyProduct(ProductInGame productInGame) {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            return advise.sendError("No puedes comprar productos en el turno de otro .", market());
        try {
            productService.buyProduct(player, productInGame);
        } catch (NoMoneyException e) {
            return advise.sendError("No tienes dinero suficiente para comprar este producto", market());
        }
        return market();
    }

    @GetMapping("/next")
    public String next() {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        Game game = getGame();
        if (loggedPlayer == player) {
            game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.RESUPPLY, player.getId()));
            gameService.saveGame(game);
        }

        return NEXT_TURN;
    }
}