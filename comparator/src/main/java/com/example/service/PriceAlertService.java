package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.model.PriceAlert;
import com.example.model.Product;

@Service
public class PriceAlertService {
    private final Map<String, List<PriceAlert>> userAlerts = new HashMap<>();

    /**
     * Saves a new price alert for a user
     * 
     * @param userId the user that wants to save the alert
     * @param alert the price alert that will be added to the user's list
     */
    public void saveAlert(String userId, PriceAlert alert) {
        userAlerts.computeIfAbsent(userId, k -> new ArrayList<>()).add(alert);
    }

    /**
     * Check all saved alerts of a user with the current list of products
     * Returns all products that are equal or lower than the price alert
     * 
     * @param userId the ID of the user whose alerts should be evaluated
     * @param allProducts the current list of all available products
     * @return a list of products that triggered one or more of the user's alerts
     */
    public List<Product> getTriggeredAlerts(String userId, List<Product> allProducts) {
        List<Product> resultedProducts = new ArrayList<>();

        List<PriceAlert> alerts = userAlerts.getOrDefault(userId, new ArrayList<>());

        for (PriceAlert alert : alerts) {
            List<Product> matchingProducts = allProducts.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(alert.getProductName()))
                    .filter(p -> p.getPrice() <= alert.getTargetPrice())
                    .collect(Collectors.toList());

            resultedProducts.addAll(matchingProducts);
        }

        return resultedProducts;
    }

    /**
     * Returns all currently saved price alerts for a user
     * 
     * @return a list of all alerts for a user
     */
    public List<PriceAlert> getAllPriceAlertsForUser(String userId) {
        return userAlerts.getOrDefault(userId, new ArrayList<>());
    }
}
