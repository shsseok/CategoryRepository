package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "category")
    private List<Food> foodList= new ArrayList<>();
    @OneToMany(mappedBy = "parentCategory")
    List<Category> childCategory = new ArrayList<>();
    @Builder
    public Category(String categoryName, Category parentCategory) {
        this.categoryName = categoryName;
        this.parentCategory = parentCategory;
    }
}
