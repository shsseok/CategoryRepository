package com.example.demo.util;

import com.example.demo.domain.Category;
import com.example.demo.domain.Food;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FoodRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryLoadService {
    private final CategoryRepository categoryRepository;
    private final FoodRepository foodRepository;
    public void saveDataFromCSV(File file)
    {
      log.info("fileLoadCheck={}",file.exists());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int dataCount=0;
            while ((line = reader.readLine()) != null) {
                System.out.println("line = " + line);
                dataCount++;
                String[] data=line.split(",");
                String parentCate=data[0];
                String childCate=data[1];
                String foodData=data[2];
                Category category=null;
                if (!categoryRepository.existsByCategoryName(parentCate))//최상위 카테고리가 없을 때
                {
                        Category findCategory = categoryRepository.findByCategoryName(parentCate);
                        category=findCategory;
                        Category category1=Category.builder()
                                .categoryName(parentCate)
                                .parentCategory(null)
                                .build();
                        categoryRepository.save(category1);
                }
                category=categoryRepository.findByCategoryName(parentCate);
                if (!categoryRepository.existsByCategoryName(childCate))
                {

                    Category category2=Category.builder()
                            .categoryName(childCate)
                            .parentCategory(category)
                            .build();
                    categoryRepository.save(category2);
                }
                category=categoryRepository.findByCategoryName(childCate);
                Food food=new Food();
                food.setFoodName(foodData);
                food.setCategory(category);
                foodRepository.save(food);
                

            }
            log.info("총 데이터 개수={}",dataCount);
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리
        }
    }

}
