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

    public void saveAlert(PriceAlert alert) {
        alerts.add(alert);
    }

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

    public List<PriceAlert> getAllPriceAlerts() {
        return alerts;
    }
}
