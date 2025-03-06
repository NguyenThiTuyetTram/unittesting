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
    private ProductRepository productRepository; // Giả lập repository

    @InjectMocks
    private ProductService productService; // Inject mock vào service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Khởi tạo mock
    }

    @Test
    void testGetAllProducts() {
        // Giả lập dữ liệu trong database
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "Laptop Dell", 1500.0),
                new Product(2L, "iPhone 15", 1200.0)
        );
        when(productRepository.findAll()).thenReturn(mockProducts); // Khi gọi findAll() -> Trả về danh sách giả lập

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size()); // Kiểm tra số lượng sản phẩm trả về
        assertEquals("Laptop Dell", products.get(0).getName()); // Kiểm tra tên sản phẩm đầu tiên
    }

    @Test
    void testGetProductById_Found() {
        Product product = new Product(1L, "Laptop Dell", 1500.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product)); // Khi gọi findById(1) -> Trả về product

        Optional<Product> foundProduct = productService.getProductById(1L);

        assertTrue(foundProduct.isPresent()); // Kiểm tra sản phẩm có tồn tại
        assertEquals("Laptop Dell", foundProduct.get().getName()); // Kiểm tra tên sản phẩm
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty()); // Khi gọi findById(1) -> Không có dữ liệu

        Optional<Product> foundProduct = productService.getProductById(1L);

        assertFalse(foundProduct.isPresent()); // Kiểm tra không tìm thấy sản phẩm
    }

    @Test
    void testCreateProduct() {
        Product product = new Product(1L, "Laptop Dell", 1500.0);
        when(productRepository.save(any(Product.class))).thenReturn(product); // Khi gọi save() -> Trả về product

        Product savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct);
        assertEquals("Laptop Dell", savedProduct.getName());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L); // Giả lập không làm gì khi xóa sản phẩm

        assertDoesNotThrow(() -> productService.deleteProduct(1L)); // Kiểm tra không có exception
    }
}
