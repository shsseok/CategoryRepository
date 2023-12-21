package com.example.demo.repository;

import com.example.demo.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
    Category findByCategoryNameAndParentCategory(String categoryName, Category parentCategory);
    // 다른 메서드들...
}


