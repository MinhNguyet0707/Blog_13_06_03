package com.example.blog.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpsertCategoryRequest {
    private String name;
}
