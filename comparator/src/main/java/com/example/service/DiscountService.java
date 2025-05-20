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
    public List<Discount> getAllDiscounts() throws IOException {
        return CsvDiscountReader.discountReader();
    }

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
