package org.springframework.samples.nt4h.card.ability;

import com.google.common.collect.Lists;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/ability")
public class AbilityController {

    private final String PAGE_MAKE_DAMAGE = "redirect:/heroAttack/makeDamage";

    private final UserService userService;
    private final GameService gameService;
    private final PlayerService playerService;
    private final AbilityService abilityService;
    private final ProductService productService;

    public AbilityController(UserService userService, GameService gameService, PlayerService playerService, AbilityService abilityService, ProductService productService) {
        this.userService = userService;
        this.gameService = gameService;
        this.playerService = playerService;
        this.abilityService = abilityService;
        this.productService = productService;
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

    @ModelAttribute("loggedPlayer")
    public Player getLoggedPlayer() {
        return getLoggedUser().getPlayer();
    }

    @PostMapping("/loseCard")
    private String loseCard(Turn turn) {
        Player currentPlayer = getCurrentPlayer();
        AbilityInGame abilityInGame = turn.getCurrentAbility();
        Deck deck = currentPlayer.getDeck();
        deck.discardCardOnHand(abilityInGame); // Esto debe de ser un efecto
        return PAGE_MAKE_DAMAGE;
    }

    @PostMapping("/chooseEnemy")
    private String chooseEnemy(Turn turn, @RequestParam("name") String name, HttpSession session) {
        EnemyInGame enemyInGame = turn.getCurrentEnemy();
        String nextUrl = session.getAttribute("nextUrl").toString();
        if (name != null) {
            String action = session.getAttribute("name").toString();
            List<Integer> enemies = (List<Integer>) session.getAttribute(action);
            if (Objects.equals(action, "getOutEnemy")) {
                Game game = getGame();
                // Colocar el enemigo por el ??ltimo.
                EnemyInGame lastOne = game.getAllOrcsInGame().get(game.getAllOrcsInGame().size() - 1);
                game.getAllOrcsInGame().set(game.getAllOrcsInGame().size() - 1, enemyInGame);
                int index = game.getActualOrcs().indexOf(enemyInGame);
                game.getActualOrcs().set(index, lastOne);
                gameService.saveGame(game);
            }
            else if (enemies == null)
                session.setAttribute(name, Lists.newArrayList(enemyInGame.getId()));
            else
                enemies.add(enemyInGame.getId()); // TODO: comprobar, si no funciona, a??adir como string separados por comas.
        }

        return nextUrl == null ? PAGE_MAKE_DAMAGE : nextUrl;
    }

    @GetMapping("/findInDiscard")
    private String findInDiscard(Turn turn, HttpSession session) {
        // Cogemos la carta elegida de la pila de descarte.
        AbilityInGame abilityInGame = turn.getCurrentAbility();
        // La colocamos en la mano.
        Player currentPlayer = getCurrentPlayer();
        Deck deck = currentPlayer.getDeck();
        deck.getInDiscard().remove(abilityInGame);
        deck.getInHand().add(abilityInGame);
        playerService.savePlayer(currentPlayer);
        return PAGE_MAKE_DAMAGE;
    }

    @PostMapping("/chooseAbilityFromDeck")
    private String chooseAbilityFromDeck(Turn turn, HttpSession session) {
        AbilityInGame abilityInGame = turn.getCurrentAbility();
        session.setAttribute("inDeck", abilityInGame.getId());
        String nextUrl = session.getAttribute("nextUrl").toString();
        return nextUrl == null ? PAGE_MAKE_DAMAGE : nextUrl;
    }

    @PostMapping("/exchangeCards")
    private String exchangeCards(Turn turn, HttpSession session) {
        AbilityInGame inDeck = abilityService.getAbilityInGameById(((Integer)session.getAttribute("inDeck")));
        AbilityInGame inHand = turn.getCurrentAbility();
        Deck deck = getCurrentPlayer().getDeck();
        deck.getInHand().remove(inHand);
        deck.getInHand().add(inDeck);
        deck.getInDeck().remove(inDeck);
        deck.getInDeck().add(inHand);
        playerService.savePlayer(getCurrentPlayer());
        return PAGE_MAKE_DAMAGE;
    }

    @PostMapping("/chooseProductFromMarket")
    private String chooseProductFromMarket(Turn turn, HttpSession session) {
        AbilityInGame abilityInGame = turn.getCurrentAbility();
        session.setAttribute("inMarket", abilityInGame.getId());
        String nextUrl = session.getAttribute("nextUrl").toString();
        return nextUrl == null ? PAGE_MAKE_DAMAGE : nextUrl;
    }

    @PostMapping("/exchangeProducts")
    private String exchangeProducts(Turn turn, HttpSession session) {
        ProductInGame inMarket = productService.getProductInGameById(((Integer) session.getAttribute("inMarket")));
        ProductInGame inSale = turn.getCurrentProduct();
        Integer id = inSale.getId();
        inSale.setId(inMarket.getId());
        inMarket.setId(id);
        productService.saveProductInGame(inSale);
        productService.saveProductInGame(inMarket);
        return PAGE_MAKE_DAMAGE;
    }

    @GetMapping("/{cardId}")
    private String findEffect(@PathVariable("cardId") Integer cardId) {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_MAKE_DAMAGE;
        AbilityInGame abilityInGame = abilityService.getAbilityInGameById(cardId);
        return "/abilities/" + abilityInGame.getAbility().getPathName();
    }
}
