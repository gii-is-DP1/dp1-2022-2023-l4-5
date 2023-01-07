package org.springframework.samples.nt4h.card.product;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.BuyProduct;
import org.springframework.samples.nt4h.card.product.exceptions.NotInSaleException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductInGameRepository productInGameRepository;

    // Product
    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Transactional(rollbackFor = {NoMoneyException.class, NotInSaleException.class})
    public void buyProduct(Player player, ProductInGame productInGame) throws NoMoneyException, NotInSaleException {
        ProductInGame selectedProduct = getProductInGameById(productInGame.getId());
        Action buy = new BuyProduct(player, selectedProduct);
        if (Objects.requireNonNull(selectedProduct.getStateProduct()) == StateProduct.IN_SALE) {
            if (player.getStatistic().getGold() < selectedProduct.getProduct().getPrice()) {
                throw new NoMoneyException();
            }
            buy.executeAction();
        } else if (Objects.requireNonNull(selectedProduct.getStateProduct()) != StateProduct.IN_SALE) {
            throw new NotInSaleException();
        }
    }

    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
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
    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
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
        productInGame.onDeleteSetNull();
        productInGameRepository.save(productInGame);
        productInGameRepository.delete(productInGame);
    }

    @Transactional
    public void deleteProductInGameById(int id) {
        ProductInGame productInGame = getProductInGameById(id);
        deleteProductInGame(productInGame);
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

    @Transactional
    public void addProduct(Game game) {
        getAllProducts().forEach(
            product -> IntStream.range(0, product.getQuantity()).forEach(i -> {
                ProductInGame productInGame = ProductInGame.builder().product(product).game(game).stateProduct(StateProduct.IN_SALE).timesUsed(0).build();
                productInGame.setName(product.getName());
                saveProductInGame(productInGame);
            }
        ));
    }
}
