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
import com.example.onlineshopping.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final CategoryModelMapper categoryModelMapper;
    private final BookRepository bookRepository;

    public CategoryResponseDto create(CategoryCreateDto categoryCreateDto) {

        Category category = new Category();
        Category byName = categoryRepository.findByName(categoryCreateDto.getName());
        if (byName != null) {
            throw new RuntimeException("Category name must be unique");
        }
        category.setName(categoryCreateDto.getName());
        categoryRepository.save(category);
        return categoryModelMapper.toResponse(category);

    }

    public CommonResponse delete(UUID id) {

        List<Book> bookList = bookRepository.getBookListByCategoryId(id);

        if (bookList.size() != 0){
            System.out.println(bookList);
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
            Optional<Category> optionalCategory = categoryRepository.findById(categoryUpdateDto.getId());
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                category.setName(categoryUpdateDto.getName());
                categoryRepository.save(category);
                return mapToDto(category);
            } else {
                throw new NoSuchElementException("Category not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update category", e);
        }
    }

    private CategoryResponseDto mapToDto(Category category) {
        return new CategoryResponseDto(category.getId(), category.getName());
    }
}
