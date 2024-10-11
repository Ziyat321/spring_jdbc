package com.practice.springjdbcziyat.dao;

import com.practice.springjdbcziyat.model.Category;
import com.practice.springjdbcziyat.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();
    Product findById(int id);
    Product create(Product product);
}
