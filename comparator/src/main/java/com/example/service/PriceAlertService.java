package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.model.PriceAlert;
import com.example.model.Product;

@Service
public class PriceAlertService {
    private final List<PriceAlert> alerts = new ArrayList<>();

    /**
     * Saves a new price alert
     * 
     * @param alert the price alert that will be added to the internal list
     */
    public void saveAlert(PriceAlert alert) {
        alerts.add(alert);
    }

    /**
     * Check all saved alerts with the current list of products
     * Returns all products that are equal or lower than the price alert
     * 
     * @param allProducts the current list of all available products
     * @return a list of products that triggered one or more alerts
     */
    public List<Product> getTriggeredAlerts(List<Product> allProducts) {
        List<Product> resultedProducts = new ArrayList<>();

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
     * Returns all currently saved price alerts
     * 
     * @return a list of all alerts
     */
    public List<PriceAlert> getAllPriceAlerts() {
        return alerts;
    }
}
