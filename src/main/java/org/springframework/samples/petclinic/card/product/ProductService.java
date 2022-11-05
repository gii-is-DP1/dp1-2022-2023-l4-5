package org.springframework.samples.petclinic.card.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product findProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Product findProductByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // TODO: Actualizar producto.

    @Transactional
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Transactional
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }
}
