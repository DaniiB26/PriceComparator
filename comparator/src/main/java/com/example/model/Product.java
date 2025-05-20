package com.example.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private String category;
    private String brand;
    private double quantity;
    private String unit;
    private double price;
    private String currency;
    private String store;
    private LocalDate date;
}
