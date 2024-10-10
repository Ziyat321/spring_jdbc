package com.practice.springjdbcziyat.dao.impl;


import com.practice.springjdbcziyat.dao.CategoryDao;
import com.practice.springjdbcziyat.exception.ExistsException;
import com.practice.springjdbcziyat.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class CategoryDaoImpl implements CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("select * from categories", this::mapRow);
    }

    @Override
    public Category findById(int id) {
        String sql = "select * from categories where id = ?";
        return jdbcTemplate.queryForStream(sql, this::mapRow, id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Категории с данным " + id + " не существует"));
    }

    @Override
    public Category create(Category category) {
        String categoryName = category.getName();
        findAll().stream()
                .filter(c -> c.getName().equals(categoryName))
                .findFirst()
                .ifPresent(c -> {throw new ExistsException("Категория с данным названием уже существует");});

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("categories")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = Map.of("name", categoryName);
        int id = insert.executeAndReturnKey(params).intValue();
        category.setId(id);
        return category;
    }

    private Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Category(id, name);
    }
}
