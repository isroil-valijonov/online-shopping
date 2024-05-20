package com.example.onlineshopping.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAllResponseDto {
    private List<CategoryResponseDto> categories;
}
