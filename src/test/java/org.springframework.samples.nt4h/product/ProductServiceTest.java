//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
/*
package org.springframework.samples.nt4h.product;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

    public ProductServiceTest() {
    }

    void createProductInGame() {
        ProductInGame product = new ProductInGame();
        Product p = this.productService.getProductById(1);
        product.setProduct(p);
        product.setStateProduct(StateProduct.INSALE);
        Game game = this.gameService.getGameById(1);
        product.setGame(game);
        product.setName("Prueba");
        product.setTimesUsed(5);
        this.productService.saveProductInGame(product);
    }

    @Test
    public void findByIDTrue() {
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
    public void shouldInsertProduct() {
        Product product = new Product();
        product.setName("Prueba");
        product.setPrice(5);
        product.setAttack(0);
        product.setQuantity(7);
        product.setMaxUses(1);
        Capacity capacity = new Capacity();
        capacity.setStateCapacity(StateCapacity.EXPERTISE);
        capacity.setLessDamage(true);
        List<Capacity> capacities = List.of(capacity);
        product.setCapacity(capacities);
        this.productService.saveProduct(product);
        Assertions.assertEquals(this.productService.getProductByName("Prueba"), product);
    }

    @Test
    void shouldUpdateProduct() {
        Product product = this.productService.getProductById(1);
        String oldName = product.getName();
        String newName = oldName + "X";
        product.setName(newName);
        this.productService.saveProduct(product);
        product = this.productService.getProductById(1);
        Assertions.assertEquals(product.getName(), newName);
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
        product.setStateProduct(StateProduct.INSALE);
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
        StateProduct newSta = StateProduct.PLAYER1;
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
    public void deleteProductTest() {
        this.productService.deleteProductById(1);
        Assertions.assertFalse(this.productService.productExists(1));
    }

    @Test
    public void deleteProductInGameTest() throws Exception {
        this.createProductInGame();
        this.productService.deleteProductInGameById(2);
        Assertions.assertFalse(this.productService.productInGameExists(2));
    }
}

 */
