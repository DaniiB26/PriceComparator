package com.example.util;

import com.example.model.Discount;
import com.example.model.Product;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

public class CsvDiscountReader {

    public static List<Discount> discountReader() throws IOException {
        List<Discount> discounts = new ArrayList<>();

        File folder = new ClassPathResource("data").getFile();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv") && name.contains("discounts"));

        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                String store = filename.split("_")[0];
                String datePart = filename.replace(store + "_discounts_", "").replace(".csv", "");
                LocalDate date = LocalDate.parse(datePart);

                try (CSVReader reader = new CSVReaderBuilder(new FileReader(file))
                        .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                        .build()) {
                    String[] line;
                    boolean isFirstLine = true;

                    while ((line = reader.readNext()) != null) {
                        if (isFirstLine) {
                            isFirstLine = false;
                            continue;
                        }

                        Product p = new Product();
                        p.setId(line[0]);
                        p.setName(line[1]);
                        p.setBrand(line[2]);
                        p.setQuantity(Double.parseDouble(line[3]));
                        p.setUnit(line[4]);
                        p.setCategory(line[5]);
                        p.setStore(store);
                        p.setDate(date);
                        p.setCurrency(null); // not present in discount file
                        p.setPrice(0.0); // not present in discount file

                        Discount d = new Discount();
                        d.setProduct(p);
                        d.setFromDate(LocalDate.parse(line[6]));
                        d.setToDate(LocalDate.parse(line[7]));
                        d.setPercentage(Integer.parseInt(line[8]));

                        discounts.add(d);
                    }
                } catch (Exception e) {
                    System.out.println("Error in the discount file: " + filename);
                    e.printStackTrace();
                }
            }
        }

        return discounts;
    }
}
