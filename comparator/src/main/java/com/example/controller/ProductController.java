package com.example.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Product;
import com.example.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAllProducts() throws IOException {
        return productService.getAllProducts();
    }

    @PostMapping("/basket/check")
    public Map<String, List<Product>> getOptimizedBasket(@RequestBody List<String> productNames) throws IOException {
        return productService.getOptimizedBasket(productNames);
    }

    @GetMapping("/{productId}/history")
    public List<Product> getProductHistory(
            @PathVariable("productId") String productId,
            @RequestParam(name = "store", required = false) String store,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "brand", required = false) String brand) throws IOException {

        return productService.getProductHistory(productId, store, category, brand);
    }

    @GetMapping("/best-value")
    public List<Product> getBestValueProducts(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "unit", required = false) String unit) throws IOException {
        return productService.getBestValueProducts(category, unit);
    }
}
