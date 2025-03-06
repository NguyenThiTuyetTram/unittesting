package com.example.unittesting;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.unittesting.Product;
import com.example.unittesting.ProductRepository;
import com.example.unittesting.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository; 

    @InjectMocks
    private ProductService productService; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    void testGetAllProducts() {
        
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "Laptop Dell", 1500.0),
                new Product(2L, "iPhone 15", 1200.0)
        );
        when(productRepository.findAll()).thenReturn(mockProducts); 

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size()); 
        assertEquals("Laptop Dell", products.get(0).getName()); 
    }

    @Test
    void testGetProductById_Found() {
        Product product = new Product(1L, "Laptop Dell", 1500.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product)); 

        Optional<Product> foundProduct = productService.getProductById(1L);

        assertTrue(foundProduct.isPresent()); 
        assertEquals("Laptop Dell", foundProduct.get().getName()); 
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty()); 

        Optional<Product> foundProduct = productService.getProductById(1L);

        assertFalse(foundProduct.isPresent()); 
    }

    @Test
    void testCreateProduct() {
        Product product = new Product(1L, "Laptop Dell", 1500.0);
        when(productRepository.save(any(Product.class))).thenReturn(product); 

        Product savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct);
        assertEquals("Laptop Dell", savedProduct.getName());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L); 

        assertDoesNotThrow(() -> productService.deleteProduct(1L)); 
    }
}
