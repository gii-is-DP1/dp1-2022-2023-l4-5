package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.BuyProduct;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
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
    public final String VIEW_MARKET = "market/market";

    private final UserService userService;
    private final ProductService productService;

    private String message = "";
    private String messageType = "";

    @Autowired
    public MarketController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @ModelAttribute("productsOnSale")
    public List<ProductInGame> getProductsInSell() {
        return productService.getMarket();
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

    @GetMapping
    public String market() {
        return VIEW_MARKET;
    }

    @PostMapping
    public String buyProduct(ProductInGame productInGame) {
        Player player = getPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (loggedPlayer != player)
            return sendError("No puedes comprar productos en el turno de otro jugador", market());
        try {
            productService.buyProduct(player, productInGame);
        } catch (NoMoneyException e) {
            return sendError("No tienes dinero suficiente para comprar este producto", market());
        }
        resetMessage();
        return market();
    }

    public String sendError(String message, String redirect) {
        this.message = message;
        messageType = "danger";
        return redirect;
    }

    private void resetMessage() {
        this.message = "";
        this.messageType = "";
    }
}
