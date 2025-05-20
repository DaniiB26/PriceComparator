package com.example.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    private Product product;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int percentage;
}
