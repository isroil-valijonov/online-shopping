package com.example.onlineshopping.category.service;

import com.example.onlineshopping.book.entity.Book;
import com.example.onlineshopping.book.repository.BookRepository;
import com.example.onlineshopping.category.dto.CategoryAllResponseDto;
import com.example.onlineshopping.category.dto.CategoryCreateDto;
import com.example.onlineshopping.category.dto.CategoryResponseDto;
import com.example.onlineshopping.category.dto.CategoryUpdateDto;
import com.example.onlineshopping.category.entity.Category;
import com.example.onlineshopping.category.mapper.CategoryModelMapper;
import com.example.onlineshopping.category.repository.CategoryRepository;
import com.example.onlineshopping.common.exceptions.CustomException;
import com.example.onlineshopping.common.response.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public CategoryResponseDto create(CategoryCreateDto categoryCreateDto) {

        try {
            Optional<Category> optionalCategory = categoryRepository.findByName(categoryCreateDto.getName());
            if (optionalCategory.isPresent()) {
                throw new DuplicateKeyException("This category already exists");
            }
            Category category = new Category();
            category.setName(categoryCreateDto.getName());
            categoryRepository.save(category);
            return mapToDto(category);
        } catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }

    }

    public CommonResponse delete(UUID id) {

        List<Book> bookList = bookRepository.getBookListByCategoryId(id);

        if (bookList.size() != 0){
            return new CommonResponse("Failed to delete category", LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        categoryRepository.deleteById(id);
        return new CommonResponse("Category has been deleted", LocalDateTime.now(), HttpStatus.OK.value());
    }


    public CategoryAllResponseDto getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDto
                = categories.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return new CategoryAllResponseDto(categoryResponseDto);
    }

    public CategoryResponseDto update(CategoryUpdateDto categoryUpdateDto) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findByName(categoryUpdateDto.getName());
            if (optionalCategory.isPresent()) {
                throw new DuplicateKeyException("Category name must be unique");
            }
            Category category = categoryRepository.findById(categoryUpdateDto.getId()).orElseThrow(() -> new CustomException("Category not found"));
            category.setName(categoryUpdateDto.getName());
            categoryRepository.save(category);
            return mapToDto(category);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }


    public CategoryResponseDto getCategoryById(UUID id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Category with id " + id + " not found", HttpStatus.NOT_FOUND.value()));
            return mapToDto(category);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }


    private CategoryResponseDto mapToDto(Category category) {
        return new CategoryResponseDto(category.getId(), category.getName());
    }
}
