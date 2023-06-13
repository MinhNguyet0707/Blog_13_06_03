package com.example.blog.service;

import com.example.blog.dto.projection.UserPublic;
import com.example.blog.entity.Image;
import com.example.blog.entity.User;
import com.example.blog.repository.ImageRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileServerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;

    public String uploadFile(Integer userId, MultipartFile file) {
        // Upload file
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found user");
                });

        try {
            Image fileServer = Image.builder()
                    .type(file.getContentType())
                    .data(file.getBytes())
                    .user(user)
                    .build();

            imageRepository.save(fileServer);

            return "/api/v1/files/" + fileServer.getId();

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload file");
        }
    }

    public Image getFileById(Integer id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found file");
                });
    }

    public void deleteFile(Integer id) {
        Image fileServer = imageRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found file");
                });
        imageRepository.delete(fileServer);
    }

    public List<Image> getFilesOfUser(Integer id) {
        User userPublic= userRepository.findById(id)
                .orElseThrow(() -> {
            throw new RuntimeException("Not found user");
        });
        return imageRepository.findByUser_Id(id);
    }

}
