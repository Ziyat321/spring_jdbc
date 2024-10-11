package com.practice.springjdbcziyat.dao;


import com.practice.springjdbcziyat.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
    Category findById(int id);
    Category create(Category category);
}
