package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Discount;
import com.example.service.DiscountService;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
    
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping()
    public List<Discount> getAllDiscounts() throws IOException {
        return discountService.getAllDiscounts();
    }

    @GetMapping("/top")
    public List<Discount> getTopDiscounts() throws IOException {
        return discountService.getTopDiscounts();
    }

    @GetMapping("/new")
    public List<Discount> getNewestDiscount() throws IOException {
        return discountService.getNewestDiscounts();
    }
}
