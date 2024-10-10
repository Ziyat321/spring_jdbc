package com.practice.springjdbcziyat.dao;


import com.practice.springjdbcziyat.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
    Category findById(int id);
    Category create(Category category);
}
