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

    /**
     * Saves a new price alert to the alert list.
     * The alert specifies a product name and a target price that the user wants to
     * monitor.
     *
     * @param alert the price alert to be saved
     */
    @PostMapping
    public void addAlert(@RequestBody PriceAlert alert) {
        alertService.saveAlert(alert);
    }

    /**
     * Returns all products that are equal or lower than the price alert
     * 
     * @param allProducts the current list of all available products
     * @return a list of products that triggered one or more alerts
     */
    @GetMapping("/triggered")
    public List<Product> getTriggeredAlerts() throws IOException {
        List<Product> allProducts = productService.getAllProducts();
        return alertService.getTriggeredAlerts(allProducts);
    }

    /**
     * Returns all currently saved price alerts
     * 
     * @return a list of all alerts
     */
    @GetMapping()
    public List<PriceAlert> getAllPriceAlerts() {
        return alertService.getAllPriceAlerts();
    }
}
