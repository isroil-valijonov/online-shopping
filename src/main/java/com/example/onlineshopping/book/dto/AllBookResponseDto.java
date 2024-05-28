package com.example.onlineshopping.book.dto;

import com.example.onlineshopping.category.entity.Category;
import com.example.onlineshopping.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllBookResponseDto {
    private UUID id;
    private String description;
    private String name;
    private int year;
    private List<Category> category;
    private User authors;
}
