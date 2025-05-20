package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.PriceAlert;
import com.example.model.Product;
import com.example.service.PriceAlertService;
import com.example.service.ProductService;

@RestController
@RequestMapping("/alerts")
public class PriceAlertController {
    
    @Autowired
    private PriceAlertService alertService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public void addAlert(@RequestBody PriceAlert alert) {
        alertService.saveAlert(alert);
    }

    @GetMapping("/triggered")
    public List<Product> getTriggeredAlerts() throws IOException {
        List<Product> allProducts = productService.getAllProducts();
        return alertService.getTriggeredAlerts(allProducts);
    }

    @GetMapping()
    public List<PriceAlert> getAllPriceAlerts() {
        return alertService.getAllPriceAlerts();
    }
}
