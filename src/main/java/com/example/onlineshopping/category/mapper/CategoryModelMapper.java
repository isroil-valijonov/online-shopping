package com.example.onlineshopping.category.mapper;

import com.example.onlineshopping.category.dto.CategoryCreateDto;
import com.example.onlineshopping.category.dto.CategoryResponseDto;
import com.example.onlineshopping.category.dto.CategoryUpdateDto;
import com.example.onlineshopping.category.entity.Category;
import com.example.onlineshopping.common.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryModelMapper extends GenericMapper<Category, CategoryCreateDto, CategoryResponseDto, CategoryUpdateDto> {
    private final ModelMapper modelMapper;
    @Override
    public Category toEntity(CategoryCreateDto categoryCreateDto) {
        return modelMapper.map(categoryCreateDto, Category.class);
    }

    @Override
    public CategoryResponseDto toResponse(Category category) {
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    @Override
    public void toUpdate(CategoryUpdateDto categoryUpdateDto, Category category) {

        modelMapper.map(categoryUpdateDto, category);
    }
}
