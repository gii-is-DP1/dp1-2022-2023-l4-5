package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
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
    private final PlayerService playerService;
    private final TurnService turnService;

    @Autowired
    public MarketController(UserService userService, PlayerService playerService, TurnService turnService, ProductService productService) {
        this.playerService = playerService;
        this.userService = userService;
        this.turnService = turnService;
        this.productService = productService;
    }

    private final ProductService productService;

    @ModelAttribute("productsInSell")
    public List<ProductInGame> getProductsInSell() {
        return productService.getMarket();
    }

    @ModelAttribute("game")
    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        return getGame().getPlayer();
    }

    @GetMapping
    public String market() {
        return VIEW_MARKET;
    }

    @PostMapping
    public String buyProduct(ProductInGame productInGame) {
        Player player = getPlayer();
        productService.buyProduct(player, productInGame);

        return market();
    }

    @GetMapping("/end")
    public String endTurn() {
        Player player = getPlayer();
        return "redirect:/turn/nextPlayer";
    }
}
