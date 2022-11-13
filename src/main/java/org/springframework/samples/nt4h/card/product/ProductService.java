package org.springframework.samples.nt4h.card.product;

import lombok.AllArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Transactional(readOnly = true)
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Transactional
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
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
    public Iterable<ProductInGame> getAllProductInGameCards() {
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
}
