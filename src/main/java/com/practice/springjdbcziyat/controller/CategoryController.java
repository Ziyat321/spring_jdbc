package com.practice.springjdbcziyat.controller;

import com.practice.springjdbcziyat.dao.CategoryDao;
import com.practice.springjdbcziyat.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryDao categoryDao;

    @GetMapping
    public List<Category> findAll(){
        return categoryDao.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable int id){
        return categoryDao.findById(id);
    }

    @PostMapping
    public Category create(@RequestBody Category category){
        return categoryDao.create(category);
    }
}
