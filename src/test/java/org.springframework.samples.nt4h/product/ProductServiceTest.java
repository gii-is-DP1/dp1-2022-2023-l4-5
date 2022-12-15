//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
/*
package org.springframework.samples.nt4h.product;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.capacity.StateCapacity;
import org.springframework.samples.nt4h.card.product.Product;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
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
    protected PlayerService playerService;

    @BeforeAll
    void setUp() throws RoleAlreadyChosenException {
        Game game = new Game();
        game.setMaxPlayers(4);
        game.setMode(Mode.UNI_CLASS);
        gameService.saveGame(game);

        Player player = new Player();
        player.setGold(20);
        playerService.savePlayer(player);
    }
    public ProductServiceTest() {
    }

    void createProductInGame() {
        ProductInGame product = new ProductInGame();
        Product p = this.productService.getProductById(1);
        product.setProduct(p);
        product.setStateProduct(StateProduct.IN_SALE);
        Game game = this.gameService.getGameById(1);
        product.setGame(game);
        product.setName("Prueba");
        product.setTimesUsed(5);
        this.productService.saveProductInGame(product);
    }

    @AfterAll
    @Test
    public void findByIDTrue() {
        gameService.deleteGameById(1);
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

    @AfterAll
    @Test
    public void findByNameTrue() {
        playerService.deletePlayerById(1);
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
    public void shouldInsertProductInGame() {
        ProductInGame product = new ProductInGame();
        Product p = this.productService.getProductById(1);
        product.setProduct(p);
        product.setStateProduct(StateProduct.IN_SALE);
        Game game = this.gameService.getGameById(1);
        product.setGame(game);
        product.setName("Prueba1");
        product.setTimesUsed(5);
        this.productService.saveProductInGame(product);
        ProductInGame encontrado = (ProductInGame)this.productService.getAllProductInGameCards().get(0);
        Assertions.assertEquals(encontrado, product);
    }

    @Test
    void shouldUpdateProductInGame() {
        this.createProductInGame();
        ProductInGame p = this.productService.getProductInGameById(1);
        StateProduct newSta = StateProduct.PLAYER;
        p.setStateProduct(newSta);
        this.productService.saveProductInGame(p);
        p = this.productService.getProductInGameById(1);
        Assertions.assertEquals(p.getStateProduct(), newSta);
    }

    @Test
    void existProductInGame() {
        this.productService.productInGameExists(1);
    }

    @Test
    public void deleteProductInGameTest() throws Exception {
        this.createProductInGame();
        this.productService.deleteProductInGameById(2);
        Assertions.assertFalse(this.productService.productInGameExists(2));

    }
}

 */
