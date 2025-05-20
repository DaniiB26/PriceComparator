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
    public List<Product> getAllProducts() throws IOException {
        List<Product> products = CsvProductReader.productReader();
        System.out.println("Readed products: " + products.size());
        return products;
    }

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

    public List<Product> getProductHistory(String productId, String store, String category, String brand) throws IOException {
        List<Product> allProducts = CsvProductReader.productReader();
        
        return allProducts.stream()
                    .filter(p -> p.getId().equalsIgnoreCase(productId))
                    .filter(p -> store == null || p.getStore().equalsIgnoreCase(store))
                    .filter(p -> category == null || p.getCategory().equalsIgnoreCase(category))
                    .filter(p -> brand == null || p.getBrand().equalsIgnoreCase(brand))
                    .sorted(Comparator.comparing(Product::getDate))
                    .collect(Collectors.toList());
    }

    public List<Product> getBestValueProducts(String category, String unit) throws IOException {
        List<Product> allProducts = CsvProductReader.productReader();

        return allProducts.stream()
                    .filter(p -> category == null || p.getCategory().equalsIgnoreCase(category))
                    .filter(p -> unit == null || p.getUnit().equalsIgnoreCase(unit))
                    .sorted(Comparator.comparingDouble(p-> p.getPrice() / p.getQuantity()))
                    .limit(10)
                    .collect(Collectors.toList());
    }
}
