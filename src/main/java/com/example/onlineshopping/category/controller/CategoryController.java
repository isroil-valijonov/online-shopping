package com.example.onlineshopping.category.controller;

import com.example.onlineshopping.category.dto.CategoryAllResponseDto;
import com.example.onlineshopping.category.dto.CategoryCreateDto;
import com.example.onlineshopping.category.dto.CategoryResponseDto;
import com.example.onlineshopping.category.dto.CategoryUpdateDto;
import com.example.onlineshopping.category.service.CategoryService;
import com.example.onlineshopping.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public CategoryResponseDto create(@RequestBody CategoryCreateDto categoryCreateDto){
        CategoryResponseDto category = categoryService.create(categoryCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(category)
                .getBody();
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryResponseDto> update(@RequestBody CategoryUpdateDto categoryUpdateDto){
        CategoryResponseDto categoryUpdate = categoryService.update(categoryUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryUpdate);
    }
    @GetMapping("/all-category")
    public ResponseEntity<CategoryAllResponseDto> getAllCategory(){
        CategoryAllResponseDto allCategory = categoryService.getAllCategory();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allCategory);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable UUID id){
        System.out.println(id);
        CommonResponse categoryDelete = categoryService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryDelete);
    }
}
