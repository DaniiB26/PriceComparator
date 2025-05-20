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

    /**
     * Returns all available products by reading data from CSV files
     * @return list of all products
     * @throws IOException if product data cannot be read
     */
    @GetMapping()
    public List<Product> getAllProducts() throws IOException {
        return productService.getAllProducts();
    }

    /**
     * Returns the cheapest available option for each product in the user's shopping basket,
     * grouped by store
     * @param productNames the list of product names in the user's shopping basket
     * @return a map where the key is the store name and the value is the list of selected products from that store
     * @throws IOException if product data cannot be read
     */
    @PostMapping("/basket/check")
    public Map<String, List<Product>> getOptimizedBasket(@RequestBody List<String> productNames) throws IOException {
        return productService.getOptimizedBasket(productNames);
    }

    /**
     * Returns the price history for a given product ID across all stores and dates,
     * optionally filtered by store, category and brand
     * 
     * @param productId the id of product
     * @param store     optional store filter
     * @param category  optional category filter
     * @param brand     optional brand filter
     * @return a sorted list of products representing the price entries
     * @throws IOException if product data cannot be read
     */
    @GetMapping("/{productId}/history")
    public List<Product> getProductHistory(
            @PathVariable("productId") String productId,
            @RequestParam(name = "store", required = false) String store,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "brand", required = false) String brand) throws IOException {

        return productService.getProductHistory(productId, store, category, brand);
    }

    /**
     * Return top 10 products with the best price per unit value
     * optionally filtered by category and unit
     * 
     * @param category the product category (optionally)
     * @param unit     the product unit (optionally)
     * @return list of top 10 products sorted by lowest price per unit
     * @throws IOException if product data cannot be read
     */
    @GetMapping("/best-value")
    public List<Product> getBestValueProducts(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "unit", required = false) String unit) throws IOException {
        return productService.getBestValueProducts(category, unit);
    }
}
