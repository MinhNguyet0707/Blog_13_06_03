package com.example.blog.service;

import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.entity.User;
import com.example.blog.exception.NotFoundException;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.UpsertBlogRequest;
import com.example.blog.response.newBlogResponse;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    public Page<BlogPublic> getAllBlog(Integer page, Integer pageSize) {
        Page<BlogPublic> pageInfo = blogRepository.findBlogs(PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));
        return pageInfo;
    }

    public Page<BlogPublic> getAllOwnBlog(Integer page, Integer pageSize) {
        // TODO : Hiện tại fix userId. Sau này userId chính là user đang login
        Integer userId = 1;

        Page<BlogPublic> pageInfo = blogRepository.findByUser_IdOrderByCreatedAtDesc(
                userId,
                PageRequest.of(page - 1, pageSize)
        );

        return pageInfo;
    }

    public List<BlogPublic> getAllOwnBlog() {
        // TODO : Hiện tại fix userId. Sau này userId chính là user đang login
        Integer userId = 1;

        List<BlogPublic> pageInfo = blogRepository.findByUser_IdOrderByCreatedAtDesc(userId);
        return pageInfo;
    }

    public newBlogResponse createBlog(UpsertBlogRequest upsertBlogRequest) {
        // TODO : Hiện tại fix userId. Sau này userId chính là user đang login
        Integer userId = 1;
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Not found user"));
        List<Category> categories = categoryRepository.findByIdIn(upsertBlogRequest.getCategoryIds());
        Slugify slugify = Slugify.builder().build();
        Blog blog = Blog.builder()
                .title(upsertBlogRequest.getTitle())
                .content(upsertBlogRequest.getContent())
                .description(upsertBlogRequest.getDescription())
                .status(upsertBlogRequest.getStatus())
                .thumbnail(upsertBlogRequest.getThumbnail())
                .user(user)
                .categories(categories)
                .slug(slugify.slugify(upsertBlogRequest.getTitle()))
                .build();

        Blog newBlog = blogRepository.save(blog);
        return new newBlogResponse("/admin/blogs/" + newBlog.getId() + "/detail");
    }


    public String updateBlog(Integer id, UpsertBlogRequest upsertBlogRequest) {
        Slugify slugify = Slugify.builder().build();
        List<Category> categories = categoryRepository.findByIdIn(upsertBlogRequest.getCategoryIds());
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found blog"));
        blog.setCategories(categories);
        blog.setContent(upsertBlogRequest.getContent());
        blog.setDescription(upsertBlogRequest.getDescription());
        blog.setStatus(upsertBlogRequest.getStatus());
        blog.setSlug(slugify.slugify(upsertBlogRequest.getTitle()));
        blog.setThumbnail(upsertBlogRequest.getThumbnail());
        blog.setTitle(upsertBlogRequest.getTitle());
        blogRepository.save(blog);
        return "Successful update";
    }
    public Object deleteBlogById(Integer id) {
        Blog blogDe = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found blog"));
        blogRepository.deleteById(id);
        return "Successful delete";
    }
}

