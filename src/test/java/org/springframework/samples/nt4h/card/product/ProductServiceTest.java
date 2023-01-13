//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package org.springframework.samples.nt4h.card.product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.capacity.StateCapacity;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.product.Product;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
@TestInstance(Lifecycle.PER_CLASS)
public class ProductServiceTest {
    @Autowired
    protected ProductService productService;
    @Autowired
    protected GameService gameService;
    @Autowired
    private UserService userService;

    @Autowired
    protected PlayerService playerService;
    protected ProductInGame productInGame;
    private Integer idProductInGame;
    private Integer idGame;
    private Integer idPlayer;

    @BeforeEach
    void setUp() throws FullGameException {
        User user = userService.getUserById(1);
        Game game = Game.createGame( "Prueba",   Mode.UNI_CLASS, 2, "");
        Player player = Player.createPlayer(user, game, true);
        System.out.println("numPlayers: " + game.getPlayers().size());
        game.setFinishDate(LocalDateTime.of(2020, 1, 2, 0, 0));
        Product product = this.productService.getProductById(1);
        productInGame = ProductInGame.createProduct(product, player, game);
        gameService.saveGame(game);
        productService.saveProductInGame(productInGame);
        idProductInGame = productInGame.getId();
        idGame = game.getId();
        idPlayer = player.getId();
    }

    @Test
    public void findByIDTrue() {
        gameService.deleteGameById(idGame);
        Product product = this.productService.getProductById(2);
        Assertions.assertNotNull(product);
        Assertions.assertEquals("Poción curativa", product.getName());
    }

    @Test
    public void findByIDFalse() {
        Product product = this.productService.getProductById(1);
        Assertions.assertNotNull(product);
        Assertions.assertNotEquals("Poción curativa", product.getName());
    }

    @Test
    public void findByNameTrue() {
        playerService.deletePlayerById(idPlayer);
        Product product = this.productService.getProductByName("Poción curativa");
        Assertions.assertNotNull(product);
        Assertions.assertEquals(8, product.getPrice());
    }

    @Test
    public void findByNameFalse() {
        Product product = this.productService.getProductByName("Poción curativa");
        Assertions.assertNotNull(product);
        Assertions.assertNotEquals(2, product.getPrice());
    }

    @Test
    void shouldFindAllProduct() {
        List<Product> list = this.productService.getAllProducts();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(9, list.size());
        Assertions.assertFalse(list.isEmpty());
    }


    @Test
    void existProduct() {
        this.productService.productExists(1);
    }

    @Test
    void shouldUpdateProductInGame() {
        ProductInGame updatedProductInGame = this.productService.getProductInGameById(idProductInGame);
        StateProduct newStateProduct = StateProduct.PLAYER;
        updatedProductInGame.setStateProduct(newStateProduct);
        productService.saveProductInGame(updatedProductInGame);
        updatedProductInGame = this.productService.getProductInGameById(idProductInGame);
        Assertions.assertEquals(updatedProductInGame.getStateProduct(), newStateProduct);
    }

    @Test
    void existProductInGame() {
        this.productService.productInGameExists(idProductInGame);
    }

    @Test
    public void deleteProductInGameTest() {
        this.productService.deleteProductInGameById(idProductInGame);
        Assertions.assertFalse(this.productService.productInGameExists(idProductInGame));

    }
}

