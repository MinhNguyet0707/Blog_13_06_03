package com.example.blog.controller;

import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.Category;
import com.example.blog.request.UpsertCategoryRequest;
import com.example.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String getCategoryPage(Model model,
                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<CategoryPublic> categoryPage = categoryService.getAllCategories(page, pageSize);
        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("currentPage", page);
        return "admin/category/category-list";
    }

    @PostMapping("")
    public ResponseEntity<CategoryPublic> createCategory(@RequestBody UpsertCategoryRequest request) {
        CategoryPublic createdCategory = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryPublic> updateCategory(@PathVariable Integer id, @RequestBody UpsertCategoryRequest request) {
        CategoryPublic updatedCategory = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully.");
    }
}
