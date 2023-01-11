package org.springframework.samples.nt4h.card.product;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ProductTest {
    @Autowired
    private Validator validator;
    @Autowired
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Product name");
        product.setPrice(5);
        product.setAttack(2);
        product.setQuantity(3);
        product.setCapacity(Lists.newArrayList());
        product.setMaxUses(1);
    }

    @Test
    void testProductProperties() {
        assertThat(product.getName()).isEqualTo("Product name");
        assertThat(product.getPrice()).isEqualTo(5);
        assertThat(product.getAttack()).isEqualTo(2);
        assertThat(product.getQuantity()).isEqualTo(3);
        assertThat(product.getCapacity()).isEqualTo(Lists.newArrayList());
    }

    @Test
    void testProductConstraints() {
        // Test price constraints
        product.setPrice(0);
        assertThat(validator.validate(product)).isNotEmpty();
        product.setPrice(100);
        assertThat(validator.validate(product)).isNotEmpty();
        product.setPrice(null);
        assertThat(validator.validate(product)).isNotEmpty();
        product.setPrice(-1);
        assertThat(validator.validate(product)).isNotEmpty();
        product.setPrice(4);
        assertThat(validator.validate(product)).isEmpty();

        // Test attack constraints
        product.setAttack(-1);
        assertThat(validator.validate(product)).isNotEmpty();
        product.setAttack(100);
        assertThat(validator.validate(product)).isNotEmpty();
        product.setAttack(null);
        assertThat(validator.validate(product)).isNotEmpty();
        product.setAttack(1);
        assertThat(validator.validate(product)).isEmpty();

        // Test capacity constraints
        product.setCapacity(null);
        assertThat(validator.validate(product)).isNotEmpty();
        product.setCapacity(Lists.newArrayList());
        assertThat(validator.validate(product)).isEmpty();
        product.setCapacity(Lists.newArrayList(new Capacity()));
        assertThat(validator.validate(product)).isEmpty();

        // Test name constraints
        product.setName("");
        assertThat(validator.validate(product)).isNotEmpty();
        product.setName("a".repeat(71));
        assertThat(validator.validate(product)).isNotEmpty();
        product.setName("Product name");

        // Test quantity constraints
        product.setQuantity(-1);
        assertThat(validator.validate(product)).isNotEmpty();
        product.setQuantity(100);
        assertThat(validator.validate(product)).isEmpty();
    }

    @Test
    void testProductLifecycle() {
        // Test saving a product
        productRepository.save(product);
        assertThat(product.getId()).isNotNull();

        // Test updating an existing product
        product.setName("New name");
        productRepository.save(product);
        assertThat(productRepository.findById(product.getId()).get().getName()).isEqualTo("New name");

        // Test deleting an existing product
        productRepository.delete(product);
        assertThat(productRepository.findById(product.getId())).isEmpty();
    }

    @Test
    void testProductQueries() {
        // Test finding all products
        productRepository.save(product);
        assertThat(productRepository.findAll()).isNotEmpty();

        // Test finding a product by id
        assertThat(productRepository.findById(product.getId())).isNotEmpty();

        // Test finding a product by name
        assertThat(productRepository.findByName(product.getName())).isNotEmpty();
    }

}
