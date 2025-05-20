package com.example.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.model.Discount;
import com.example.util.CsvDiscountReader;

@Service
public class DiscountService {

    /**
     * Loads all discounts from the CSV discount reader
     * 
     * @return list of all discounts
     * @throws IOException if the CSV files cannot be accessed or read
     */
    public List<Discount> getAllDiscounts() throws IOException {
        return CsvDiscountReader.discountReader();
    }

    /**
     * Returns the top 10 discounts with the highest percentage,
     * sorted in descending order
     * 
     * @return a list of the top 10 discounts by percentage
     * @throws IOException if discount data cannot be read
     */
    public List<Discount> getTopDiscounts() throws IOException {
        try {
            List<Discount> allDiscounts = getAllDiscounts();

            return allDiscounts.stream()
                    .sorted(Comparator.comparingInt(Discount::getPercentage).reversed())
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("The discounts cannot be loaded: ", e);
        }
    }

    /**
     * Returns a list of discounts that were added in the last 24 hours
     * 
     * @return a list of newly added discounts
     * @throws IOException if discount data cannot be read
     */
    public List<Discount> getNewestDiscounts() throws IOException {
        try {
            List<Discount> allDiscounts = getAllDiscounts();

            return allDiscounts.stream()
                    .filter(d -> d.getFromDate().isAfter(LocalDate.now().minusDays(1)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("The newest discounts cannot be loaded: ", e);
        }
    }
}
