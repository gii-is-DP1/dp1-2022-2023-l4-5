package org.springframework.samples.nt4h.card.product;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.BuyProduct;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductInGameRepository productInGameRepository;

    // Product
    @Transactional(readOnly = true)
    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Transactional
    public void buyProduct(Player player, ProductInGame productInGame) throws NoMoneyException {
        if (player.getGold() < productInGame.getProduct().getPrice())
            throw new NoMoneyException();
        else if (productInGame.getStateProduct() == StateProduct.IN_SALE) {
            Action bp = new BuyProduct(player, productInGame);
            bp.executeAction();
        }
    }

    @Transactional(readOnly = true)
    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean productExists(int id) {
        return productRepository.existsById(id);
    }

    // ProductInGame
    @Transactional(readOnly = true)
    public ProductInGame getProductInGameById(int id) {
        return productInGameRepository.findById(id).orElseThrow(() -> new NotFoundException("ProductInGame not found"));
    }

    @Transactional(readOnly = true)
    public List<ProductInGame> getAllProductInGameCards() {
        return productInGameRepository.findAll();
    }

    @Transactional
    public void saveProductInGame(ProductInGame productInGame) {
        productInGameRepository.save(productInGame);
    }

    @Transactional
    public void deleteProductInGame(ProductInGame productInGame) {
        productInGameRepository.delete(productInGame);
    }

    @Transactional
    public void deleteProductInGameById(int id) {
        productInGameRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean productInGameExists(int id) {
        return productInGameRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> getMarket() {
        return productInGameRepository.findAll().stream().filter(product -> product.getStateProduct().equals(StateProduct.IN_SALE)).limit(5)
            .map(ProductInGame::getProduct).collect(Collectors.toList());
    }

    public void addProduct(Game game) {
        getAllProducts().forEach(
            product -> {
                for (int i = 0; i < product.getQuantity(); i++) {
                    ProductInGame productInGame = ProductInGame.builder().product(product).game(game).stateProduct(StateProduct.IN_SALE).timesUsed(0).build();
                    productInGame.setName(product.getName());
                    saveProductInGame(productInGame);
                }
            });
    }
}
