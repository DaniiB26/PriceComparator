package com.example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.model.Product;
import com.example.util.CsvProductReader;

@Service
public class ProductService {

    /**
     * Return all products by reading and parsing all product CSV files
     * 
     * @return a list of all products
     * @throws IOException if an error occurs while reading the CSV files
     */
    public List<Product> getAllProducts() throws IOException {
        List<Product> products = CsvProductReader.productReader();
        System.out.println("Readed products: " + products.size());
        return products;
    }

    /**
     * Optimizes a shopping basket by selecting the cheapest product from all stores
     * The products are grouped by the store
     * 
     * @param productNames the list of product names in the user's shopping basket
     * @return a map where the key is the store name and the value is the list of
     *         selected products from that store
     * @throws IOException if product data cannot be read
     */
    public Map<String, List<Product>> getOptimizedBasket(List<String> productNames) throws IOException {
        List<Product> allProducts = CsvProductReader.productReader();

        Map<String, List<Product>> storeToProducts = new HashMap<>();

        for (String name : productNames) {

            List<Product> matches = allProducts.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(name))
                    .collect(Collectors.toList());

            matches.stream()
                    .min(Comparator.comparingDouble(Product::getPrice))
                    .ifPresent(best -> {
                        storeToProducts
                                .computeIfAbsent(best.getStore(), k -> new ArrayList<>())
                                .add(best);
                    });
        }

        return storeToProducts;
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
    public List<Product> getProductHistory(String productId, String store, String category, String brand)
            throws IOException {
        List<Product> allProducts = CsvProductReader.productReader();

        return allProducts.stream()
                .filter(p -> p.getId().equalsIgnoreCase(productId))
                .filter(p -> store == null || p.getStore().equalsIgnoreCase(store))
                .filter(p -> category == null || p.getCategory().equalsIgnoreCase(category))
                .filter(p -> brand == null || p.getBrand().equalsIgnoreCase(brand))
                .sorted(Comparator.comparing(Product::getDate))
                .collect(Collectors.toList());
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
    public List<Product> getBestValueProducts(String category, String unit) throws IOException {
        List<Product> allProducts = CsvProductReader.productReader();

        return allProducts.stream()
                .filter(p -> category == null || p.getCategory().equalsIgnoreCase(category))
                .filter(p -> unit == null || p.getUnit().equalsIgnoreCase(unit))
                .sorted(Comparator.comparingDouble(p -> p.getPrice() / p.getQuantity()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
