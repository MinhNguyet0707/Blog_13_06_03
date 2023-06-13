package com.example.blog.controller;

import com.example.blog.entity.Image;
import com.example.blog.service.FileServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class ImageController {

    @Autowired
    private FileServerService fileServerService;
    @GetMapping("{id}/files")
    public ResponseEntity<?> getFiles(@PathVariable Integer id) {
        List<Image> fileServerList = fileServerService.getFilesOfUser(id);
        return ResponseEntity.ok(fileServerList);
    }

    // tải ảnh
    @PostMapping("{id}/files")
    public ResponseEntity<?> uploadFile(@ModelAttribute("file") MultipartFile file,
                                        @PathVariable Integer id) {
        return ResponseEntity.ok(fileServerService.uploadFile(id, file));
    }

    @GetMapping("files/{id}")
    public ResponseEntity<?> readFile(@PathVariable Integer id) {
        Image fileServer = fileServerService.getFileById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileServer.getType()))
                .body(fileServer.getData());

    }
    @DeleteMapping("files/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Integer id) {
        fileServerService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }


}
