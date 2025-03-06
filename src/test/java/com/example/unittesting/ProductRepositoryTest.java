package com.example.unittesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFindById() {
        Product product = new Product(null, "Laptop Dell", 1500.0);
        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Laptop Dell", savedProduct.getName());
    }

    @Test
    void testFindAll() {
        productRepository.save(new Product(null, "iPhone 15", 1200.0));
        productRepository.save(new Product(null, "MacBook Pro", 2000.0));

        List<Product> products = productRepository.findAll();
        assertEquals(2, products.size());
    }
}
