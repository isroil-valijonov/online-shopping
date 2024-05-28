package com.example.onlineshopping.book.controller;

import com.example.onlineshopping.book.dto.*;
import com.example.onlineshopping.book.service.BookService;
import com.example.onlineshopping.common.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<AllBookResponseDto> create(@RequestBody @Valid BookCreateDto bookCreateDto) throws Exception {
        AllBookResponseDto book = bookService.create(bookCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(book);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AllBookResponseDto> update(@PathVariable UUID id, @RequestBody BookUpdateDto bookUpdateDto){
        AllBookResponseDto bookResponseDto = bookService.update(id, bookUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookResponseDto);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable UUID id){
        CommonResponse commonResponse = bookService.delete(id);
        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);
    }

    @GetMapping("/all-books-in-author/{id}")
    public ResponseEntity<BookListResponseDto> getBooksByAuthor(@PathVariable UUID id) {
        BookListResponseDto response = bookService.getBookListByAuthorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-books-in-category/{id}")
    public ResponseEntity<BookListResponseDto> getBookListByCategory(@PathVariable UUID id){
        BookListResponseDto bookList = bookService.getBookListByCategoryId(id);
        return ResponseEntity.ok(bookList);
    }

    @GetMapping("/book-with-author-and-category/{id}")
    public ResponseEntity<AllBookResponseDto> getByIdWithAuthorAndCategory(@PathVariable UUID id){
        AllBookResponseDto responseDto = bookService.getBookWithAuthorAndCategory(id);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/find-by-book-id/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable UUID id){
        BookResponseDto book = bookService.getBook(id);
        return ResponseEntity.ok(book);
    }

}
