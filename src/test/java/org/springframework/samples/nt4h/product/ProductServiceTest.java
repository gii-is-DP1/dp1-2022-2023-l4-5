package org.springframework.samples.nt4h.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.capacity.StateCapacity;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.product.Product;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.ProductService;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductServiceTest {
    @Autowired
    protected ProductService productService;
    @Autowired
    protected GameService gameService;
    @Autowired
    protected PlayerService playerService;

    @Test
    public void findByIDTrue(){
        Product product = productService.getProductById(2);
        assertNotNull(product);
        assertEquals("Poción curativa",product.getName() );
    }
    @Test
    public void findByIDFalse() {
        Product product= productService.getProductById(1);
        assertNotNull(product);
        assertNotEquals("Poción curativa",product.getName());
    }
    @Test
    public void findByNameTrue() {
        Product product = productService.getProductByName("Poción curativa");
        assertNotNull(product);
        assertEquals(8,product.getPrice());
    }
    @Test
    public void findByNameFalse() {
        Product product = productService.getProductByName("Poción curativa");
        assertNotNull(product);
        assertNotEquals(2,product.getPrice());
    }
    @Test
    void shouldFindAllProduct() {
        List<Product> list = productService.getAllProducts();
        assertNotNull(list);
        assertEquals(9,list.size());
        assertFalse(list.isEmpty());
    }
    @Test
    @Transactional
    public void shouldInsertProduct() {
        Product product= new Product();
        product.setName("Prueba");
        product.setPrice(5);
        product.setAttack(0);
        product.setQuantity(7);
        product.setMaxUses(10);
        Capacity capacity= new Capacity();
        capacity.setStateCapacity(StateCapacity.EXPERTISE);
        capacity.setLessDamage(true);
        List<Capacity>capacities= List.of(capacity);
        product.setCapacity(capacities);
        this.productService.saveProduct(product);
        assertEquals(productService.getProductByName("Prueba"),product);

    }
    @Test
    @Transactional
    void shouldUpdateProduct(){
        Product product = this.productService.getProductById(1);
        String oldName = product.getName();
        String newName = oldName + "X";
        product.setName(newName);
        this.productService.saveProduct(product);
        product= this.productService.getProductById(1);
        assertEquals(product.getName(),newName);
    }
    @Test
    @Transactional
    void existProduct(){
        productService.productExists(1);
    }

    //ProductInGame
    @BeforeAll
    void createProductInGame() throws RoleAlreadyChosenException {
        ProductInGame product= new ProductInGame();
        Product p = productService.getProductById(1);
        product.setProduct(p);
        product.setStateProduct(StateProduct.INSALE);
        Game game = gameService.getGameById(1);
        product.setGame(game);
        Player player = new Player();
        playerService.savePlayer(player);
        product.setPlayer(player);
        product.setName("Prueba");
        product.setTimesUsed(5);
        productService.saveProductInGame(product);
    }
    @Test
    @Transactional
    public void shouldInsertProductInGame(){
        ProductInGame product= new ProductInGame();
        Product p = productService.getProductById(1);
        product.setProduct(p);
        product.setStateProduct(StateProduct.INSALE);
        Game game = gameService.getGameById(1);
        product.setGame(game);
        Player player = new Player();
        product.setPlayer(player);
        product.setName("Prueba2");
        product.setTimesUsed(5);
        productService.saveProductInGame(product);
        assertEquals(productService.getProductInGameById(2),product);

    }
    @Test
    @Transactional
    void shouldUpdateProductInGame(){
        ProductInGame product= productService.getProductInGameById(1);
        StateProduct newSta= StateProduct.PLAYER1;
        product.setStateProduct(newSta);
        productService.saveProductInGame(product);
        product= productService.getProductInGameById(1);
        assertEquals(product.getStateProduct(),newSta);
    }
    @Test
    @Transactional
    void existProductInGame(){
        productService.productInGameExists(1);
    }
    @Test
    public void deleteProductTest(){
        productService.deleteProductById(1);
        assertThrows(DataIntegrityViolationException.class,()->productService.productExists(1));
    }
    @Test
    public void deleteProductInGameTest(){
        productService.deleteProductInGameById(1);
        assertFalse(productService.productInGameExists(1));
    }

}
