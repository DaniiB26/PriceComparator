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

    /**
     * Returns all discounts
     * 
     * @return list of all discounts
     * @throws IOException if the CSV files cannot be accessed or read
     */
    @GetMapping()
    public List<Discount> getAllDiscounts() throws IOException {
        return discountService.getAllDiscounts();
    }

    /**
     * Returns the top 10 discounts with the highest percentage,
     * sorted in descending order
     * 
     * @return a list of the top 10 discounts by percentage
     * @throws IOException if discount data cannot be read
     */
    @GetMapping("/top")
    public List<Discount> getTopDiscounts() throws IOException {
        return discountService.getTopDiscounts();
    }

    /**
     * Returns a list of discounts that were added in the last 24 hours
     * 
     * @return a list of newly added discounts
     * @throws IOException if discount data cannot be read
     */
    @GetMapping("/new")
    public List<Discount> getNewestDiscount() throws IOException {
        return discountService.getNewestDiscounts();
    }
}
