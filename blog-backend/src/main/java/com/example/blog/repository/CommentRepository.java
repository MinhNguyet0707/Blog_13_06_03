package com.example.blog.repository;

import com.example.blog.entity.Blog;
import com.example.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    void deleteCommentsByBlog(Blog blog);
}