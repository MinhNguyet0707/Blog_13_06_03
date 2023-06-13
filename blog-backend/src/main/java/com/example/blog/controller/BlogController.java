package com.example.blog.controller;

import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.request.UpsertBlogRequest;
import com.example.blog.service.BlogService;
import com.example.blog.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final CategoryService categoryService;

    // Danh sách tất cả bài viết
    @GetMapping("/admin/blogs")
    public String getBlogPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<BlogPublic> pageInfo = blogService.getAllBlog(page, pageSize);
        model.addAttribute("page", pageInfo);
        model.addAttribute("currentPage", page);
        return "admin/blog/blog-index";
    }

    // Danh sách bài viết của tôi
    @GetMapping("/admin/blogs/own-blogs")
    public String getOwnBlogPage(Model model) {
        List<BlogPublic> blogList = blogService.getAllOwnBlog();
        model.addAttribute("blogList", blogList);
        return "admin/blog/own-blog";
    }



    // Chi tiết bài viết theo id
    @GetMapping("/admin/blogs/{id}/detail")
    public String getBlogDetailPage(@PathVariable Integer id, Model model) {
        return "admin/blog/blog-detail";
    }
    // Tạo bài viết
    @GetMapping("/admin/blogs/create")
    public String getBlogCreatePage(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "admin/blog/blog-create";
    }
    //thêm blog mới
    @PostMapping("/api/v1/admin/blogs")
    public ResponseEntity<?> createBlog(@RequestBody UpsertBlogRequest upsertBlogRequest) {
        return ResponseEntity.status(201).body(blogService.createBlog(upsertBlogRequest));
    }

  // Cập nhât blog
  @PutMapping("/api/v1/admin/blogs/{id}")
  public ResponseEntity<?> updateBlog(@PathVariable Integer id, @RequestBody UpsertBlogRequest upsertBlogRequest) {

      return ResponseEntity.ok(blogService.updateBlog(id, upsertBlogRequest));
  }

  // Xóa blog (xóa blog xóa luôn comment liên quan đến blog)
    @DeleteMapping("/api/v1/admin/blogs/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id) {
        return ResponseEntity.ok(blogService.deleteBlogById(id));
    }

}
