package com.poly.sof309.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * =========================================================================
 * BÀI TEST MINH HỌA KIỂM THỬ ĐƠN VỊ CHO CHỨC NĂNG CRUD SẢN PHẨM (SLIDE 1)
 * Giống hệt bài demo trong ảnh của Thầy Bình (SOF309)
 * =========================================================================
 */
public class ProductControllerTest {
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        ProductService productService = new ProductService();
        productController = new ProductController(productService);
    }

    @Test
    @DisplayName("TC_PROD_01: Thêm mới sản phẩm hợp lệ thành công")
    public void testAddProductSuccess() {
        Product newProd = new Product("P03", "Mũ bảo hiểm Eco Smart", 350000.0, 20, "ACTIVE");
        Product created = productController.addProduct(newProd);

        assertNotNull(created);
        // Lưu ý: Nếu muốn xem vệt đỏ so sánh Diff [P13] vs [P03] y hệt ảnh Thầy, đổi "P03" thành "P13"
        assertEquals("P03", created.getId());
        assertEquals("Mũ bảo hiểm Eco Smart", created.getName());
    }

    @Test
    @DisplayName("TC_PROD_02: Ném ngoại lệ khi thêm sản phẩm có giá <= 0")
    public void testAddProductInvalidPriceThrowsException() {
        Product invalidProd = new Product("P04", "Găng tay đi xe", -50000.0, 10, "ACTIVE");

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            productController.addProduct(invalidProd);
        });
        assertNotNull(ex);
    }

    @Test
    @DisplayName("TC_PROD_03: Tìm kiếm sản phẩm theo ID thành công")
    public void testGetProductByIdSuccess() {
        Product found = productController.getProductById("P01");
        assertNotNull(found);
        assertEquals("P01", found.getId());
    }

    @Test
    @DisplayName("TC_PROD_04: Xóa sản phẩm thành công")
    public void testDeleteProductSuccess() {
        boolean deleted = productController.deleteProduct("P02");
        assertTrue(deleted);
    }

    @Test
    @DisplayName("TC_PROD_05: Lấy danh sách tất cả sản phẩm")
    public void testListProducts() {
        List<Product> list = productController.getAllProducts();
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    // =========================================================================
    // CÁC LỚP MODEL, SERVICE, CONTROLLER PHỤ TRỢ ĐỂ CHẠY TEST TRỰC TIẾP
    // =========================================================================

    public static class Product {
        private String id;
        private String name;
        private double price;
        private int quantity;
        private String status;

        public Product() {}

        public Product(String id, String name, double price, int quantity, String status) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.status = status;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }

    public static class ProductService {
        private final List<Product> products = new ArrayList<>();

        public ProductService() {
            products.add(new Product("P01", "Áo mưa cao cấp", 120000.0, 50, "ACTIVE"));
            products.add(new Product("P02", "Bọc chìa khóa", 25000.0, 100, "ACTIVE"));
        }

        public Product add(Product product) {
            if (product.getPrice() <= 0) {
                throw new ValidationException("Giá sản phẩm phải lớn hơn 0");
            }
            products.add(product);
            return product;
        }

        public Product findById(String id) {
            for (Product p : products) {
                if (p.getId().equals(id)) return p;
            }
            return null;
        }

        public boolean delete(String id) {
            return products.removeIf(p -> p.getId().equals(id));
        }

        public List<Product> findAll() {
            return new ArrayList<>(products);
        }
    }

    public static class ProductController {
        private final ProductService service;

        public ProductController(ProductService service) {
            this.service = service;
        }

        public Product addProduct(Product product) {
            return service.add(product);
        }

        public Product getProductById(String id) {
            return service.findById(id);
        }

        public boolean deleteProduct(String id) {
            return service.delete(id);
        }

        public List<Product> getAllProducts() {
            return service.findAll();
        }
    }
}
