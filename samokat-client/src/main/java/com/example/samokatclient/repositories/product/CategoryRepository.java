package com.example.samokatclient.repositories.product;

import com.example.samokatclient.entities.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParentIsNull();
}
