package com.practice.springjdbcziyat.dao.impl;


import com.practice.springjdbcziyat.dao.CategoryDao;
import com.practice.springjdbcziyat.exception.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException("Категории с данным " + id + " не существует"));
    }

    @Override
    public Category create(Category category) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("categories")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = Map.of("name", category.getName());
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
