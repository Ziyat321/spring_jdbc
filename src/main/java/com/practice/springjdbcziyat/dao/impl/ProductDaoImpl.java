package com.practice.springjdbcziyat.dao.impl;

import com.practice.springjdbcziyat.dao.ProductDao;
import com.practice.springjdbcziyat.exception.NotFoundException;
import com.practice.springjdbcziyat.model.Category;
import com.practice.springjdbcziyat.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {
    private final JdbcTemplate jdbcTemplate;

    private final static String SELECT = """
            select p.id as product_id,
                   p.name as product_name,
                   p.price as product_price,
                   c.id as category_id,
                   c.name as category_name
            from products as p
            inner join categories as c on c.id = p.category_id
            """;

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SELECT, this::mapRow);
    }

    @Override
    public Product findById(int id) {
        String sql = SELECT + " where p.id = ?";
        return jdbcTemplate.queryForStream(sql, this::mapRow, id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Категории с данным " + id + " не существует"));
    }

    @Override
    public Product create(Product product) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = Map.of("name", product.getName(),
                "price", product.getPrice(),
                "category_id", product.getCategory().getId());
        int id = insert.executeAndReturnKey(params).intValue();
        product.setId(id);
        return product;
    }

    private Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("product_id");
        String name = rs.getString("product_name");
        double price = rs.getDouble("product_price");
        int categoryId = rs.getInt("category_id");
        String categoryName = rs.getString("category_name");
        return new Product(id, name, price, new Category(categoryId, categoryName));
    }
}
