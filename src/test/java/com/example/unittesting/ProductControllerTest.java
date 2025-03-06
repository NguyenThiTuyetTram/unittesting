package com.example.unittesting;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(ProductController.class) // Chỉ test Controller
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(
                new Product(1L, "Laptop Dell", 1500.0),
                new Product(2L, "iPhone 15", 1200.0)
        ));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk()) // Kiểm tra HTTP 200
                .andExpect(jsonPath("$.size()", is(2))) // Kiểm tra có 2 sản phẩm
                .andExpect(jsonPath("$[0].name", is("Laptop Dell"))); // Kiểm tra tên sản phẩm đầu tiên
    }

    @Test
    void testGetProductById_Found() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.of(new Product(1L, "Laptop Dell", 1500.0)));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Laptop Dell")));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product(1L, "Laptop Dell", 1500.0);
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Laptop Dell")));
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        Product updatedProduct = new Product(1L, "MacBook Pro", 2000.0);
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("MacBook Pro")));
    }

    @Test
    void testUpdateProduct_NotFound() throws Exception {
        when(productService.updateProduct(eq(1L), any(Product.class))).thenThrow(new RuntimeException("Not Found"));

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Product(1L, "MacBook Pro", 2000.0))))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
