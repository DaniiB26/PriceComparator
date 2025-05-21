package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param userId id of the user
     * @param alert  the price alert to be saved
     */
    @PostMapping
    public void addAlert(@RequestParam(name = "userId") String userId, @RequestBody PriceAlert alert) {
        alertService.saveAlert(userId, alert);
    }

    /**
     * Returns all products that are equal or lower than the price alert
     *
     * @param userId id of the user
     * @return a list of products that triggered one or more alerts
     */
    @GetMapping("/triggered")
    public List<Product> getTriggeredAlerts(@RequestParam(name = "userId") String userId) throws IOException {
        List<Product> allProducts = productService.getAllProducts();
        return alertService.getTriggeredAlerts(userId, allProducts);
    }

    /**
     * Returns all currently saved price alerts
     * 
     * @param userId id of the user
     * @return a list of all alerts
     */
    @GetMapping
    public List<PriceAlert> getAllPriceAlertsForUser(@RequestParam(name = "userId") String userId) {
        return alertService.getAllPriceAlertsForUser(userId);
    }
}
