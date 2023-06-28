package com.example.blog.controller;

import com.example.blog.request.LoginRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("login-handle")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        log.info("request:{}", request);
        // tạo đối tượng xác thựuc
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        );
        // tiến hành xác thực
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            //luu vao context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            session.setAttribute("MY_SESSION", authentication.getName()); // Lưu email -> session


            return ResponseEntity.ok("thanh cong");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login fail");
        }

    }
}
