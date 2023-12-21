package com.example.demo.service;

import com.example.demo.domain.Category;
import com.example.demo.domain.Product; // Product 엔티티를 import 해야 합니다.
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository; // ProductRepository를 import 해야 합니다.
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList; // ArrayList를 import 해야 합니다.

// 나머지 import 구문들...

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public void saveDataFromCSV(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVReader csvReader = new CSVReader(reader);
            String[] nextRecord;

            Category currentCategory = null;

            while ((nextRecord = csvReader.readNext()) != null) {
                String topCategoryName = nextRecord[0];
                String subCategoryName = nextRecord[1];
                String productName = nextRecord[2];

                if (currentCategory == null || !currentCategory.getCategoryName().equals(topCategoryName)) {
                    currentCategory = categoryRepository.findByCategoryName(topCategoryName);
                    if (currentCategory == null) {
                        currentCategory = new Category();
                        currentCategory.setCategoryName(topCategoryName);
                        currentCategory.setProducts(new ArrayList<>()); // ArrayList를 생성해야 합니다.
                        categoryRepository.save(currentCategory);
                    }
                }

                Category subCategory = categoryRepository.findByCategoryNameAndParentCategory(subCategoryName, currentCategory);
                if (subCategory == null) {
                    subCategory = new Category();
                    subCategory.setCategoryName(subCategoryName);
                    subCategory.setParentCategory(currentCategory);
                    subCategory.setProducts(new ArrayList<>()); // ArrayList를 생성해야 합니다.
                    categoryRepository.save(subCategory);
                }

                Product product = new Product(); // Product 객체를 생성해야 합니다.
                product.setProductName(productName);
                product.setCategory(subCategory);
                productRepository.save(product); // ProductRepository를 통해 product를 저장해야 합니다.
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
