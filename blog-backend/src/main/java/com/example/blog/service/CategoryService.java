package com.example.blog.service;

import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.Category;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.request.UpsertCategoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
    public class CategoryService {
    @Autowired
        private CategoryRepository categoryRepository;


        public Page<CategoryPublic> getAllCategories(Integer page, Integer pageSize) {
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            Page<Category> categoryPage = categoryRepository.findAll(pageable);
            return categoryPage.map(CategoryPublic::of);
        }

        public CategoryPublic createCategory(UpsertCategoryRequest request) {
            // Kiểm tra nếu tên category đã tồn tại
            if (categoryRepository.existsByName(request.getName())) {
                throw new IllegalArgumentException("Category name already exists");
            }

            Category category = new Category();
            category.setName(request.getName());

            Category savedCategory = categoryRepository.save(category);
            return CategoryPublic.of(savedCategory);
        }

        public CategoryPublic updateCategory(Integer id, UpsertCategoryRequest request) {
            // Kiểm tra nếu tên category đã tồn tại
            if (categoryRepository.existsByNameAndIdNot(request.getName(), id)) {
                throw new IllegalArgumentException("Category name already exists");
            }

            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Category not found"));

            category.setName(request.getName());

            Category updatedCategory = categoryRepository.save(category);
            return CategoryPublic.of(updatedCategory);
        }

        public void deleteCategory(Integer id) {
            // Xóa liên kết Category - Blog trong bảng trung gian
            categoryRepository.deleteCategoryLinks(id);
            // Xóa Category
            categoryRepository.deleteById(id);
        }


    public List<CategoryPublic> getCategories() {
        return categoryRepository.findAll().stream().map(CategoryPublic::of).toList();
    }

}

