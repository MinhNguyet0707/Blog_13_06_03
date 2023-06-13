package com.example.blog.repository;

import com.example.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByIdIn(List<Integer> ids);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);


    @Modifying
    @Query(value = "DELETE FROM blog_category WHERE category_id = :categoryId", nativeQuery = true)
    void deleteCategoryLinks(@Param("categoryId") Integer categoryId);


}