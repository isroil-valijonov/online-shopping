package com.example.onlineshopping.category.repository;

import com.example.onlineshopping.category.entity.Category;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query(value = "SELECT * FROM Category WHERE name = :name", nativeQuery = true)
    Category findByName(@Param("name") String name);
}
