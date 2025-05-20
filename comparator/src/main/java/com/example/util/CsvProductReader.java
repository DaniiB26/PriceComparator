package com.example.util;

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

public class CsvProductReader {

    /**
     * Reads all CSV files located in the "data" folder (excluding discount files)
     * and parses them into a list of Product objects
     * 
     * The name format helps to extract the store name and date for each product
     * 
     * @return a list of products extracted from all valid CSV files
     * @throws IOException if the data folder or any of the files cannot be accessed or read
     */
    public static List<Product> productReader() throws IOException {
        List<Product> products = new ArrayList<>();

        File folder = new ClassPathResource("data").getFile();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv") && !name.contains("discount"));

        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                String store = filename.split("_")[0];
                String datePart = filename.replace(store + "_", "").replace(".csv", "");
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

                        Product product = new Product(
                                line[0], // id
                                line[1], // name
                                line[2], // category
                                line[3], // brand
                                Double.parseDouble(line[4]), // quantity
                                line[5], // unit
                                Double.parseDouble(line[6]), // price
                                line[7], // currency
                                store, // store
                                date // date
                        );

                        products.add(product);
                    }
                } catch (Exception e) {
                    System.out.println("Error in the product file: " + filename);
                    e.printStackTrace();
                }
            }
        }

        return products;
    }
}
