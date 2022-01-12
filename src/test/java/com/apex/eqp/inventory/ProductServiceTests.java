package com.apex.eqp.inventory;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.entities.RecalledProduct;
import com.apex.eqp.inventory.repositories.RecalledProductRepository;
import com.apex.eqp.inventory.services.ProductService;
import com.apex.eqp.inventory.services.RecalledProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
class ProductServiceTests {

    @Autowired
    ProductService productService;

    @Autowired
    RecalledProductService recalledProductService;
    
    @Autowired
    RecalledProductRepository recalledProductRepo;

    /**
     * Helper method to create test products
     */
    private Product createTestProduct(String productName, Double price, Integer quantity) {
        return Product.builder()
                .name(productName)
                .price(price)
                .quantity(quantity)
                .build();
    }

    /**
     * Helper method to create test recalled products
     */
    private RecalledProduct createTestRecalledProduct(String recalledProductName) {
        return RecalledProduct.builder()
                .name(recalledProductName)
                .build();
    }

    @Test
    void shouldSaveProduct() {
        Product product = createTestProduct("product1", 1.2, 2);

        Product savedProduct = productService.save(product);

        Product loadedProduct = productService.findById(savedProduct.getId()).orElse(null);

        Assertions.assertNotNull(loadedProduct);
        Assertions.assertNotNull(loadedProduct.getId());
    }

    @Test
    void shouldUpdateProduct() {
        Product product = createTestProduct("product2", 1.3, 5);

        Product savedProduct = productService.save(product);

        Product loadedProduct = productService.findById(savedProduct.getId()).orElse(null);

        Assertions.assertNotNull(loadedProduct);

        loadedProduct.setName("NewProduct");

        productService.save(loadedProduct);

        Assertions.assertNotNull(productService.findById(loadedProduct.getId()).orElse(null));
    }
    
    @Test
    void getAllNonRecalledProducts() {
    	recalledProductRepo.save(createTestRecalledProduct("Recalled-One"));
    	recalledProductRepo.save(createTestRecalledProduct("Recalled-Two"));
    	
    	productService.save(createTestProduct("Non-Recalled",10.1, 3));
    	productService.save(createTestProduct("Recalled-One",10.1, 3));
    	
    	Collection<Product> nonRecalledProducts = productService.getAllProduct();
    	
    	HashSet<String> recalledSet = new HashSet<>();
    	recalledSet.add("Recalled-One");
    	recalledSet.add("Recalled-Two");
    	
    	for(Product product: nonRecalledProducts) {
    		Assertions.assertFalse(recalledSet.contains(product.getName()));
    		Assertions.assertEquals("Non-Recalled", product.getName());
    	}
    	
    	
    	
    }
}
