package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

}
