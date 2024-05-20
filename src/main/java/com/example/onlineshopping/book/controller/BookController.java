package com.example.onlineshopping.book.controller;

import com.example.onlineshopping.book.dto.AllBookResponseDto;
import com.example.onlineshopping.book.dto.BookCreateDto;
import com.example.onlineshopping.book.dto.BookResponseDto;
import com.example.onlineshopping.book.dto.BookUpdateDto;
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
    public ResponseEntity<BookResponseDto> create(@RequestBody @Valid BookCreateDto bookCreateDto) throws Exception {
        BookResponseDto book = bookService.create(bookCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(book);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookResponseDto> update(@PathVariable UUID id, @RequestBody BookUpdateDto bookUpdateDto){
        BookResponseDto bookResponseDto = bookService.update(id, bookUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookResponseDto);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable UUID id){
        CommonResponse commonResponse = bookService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commonResponse);
    }

    @GetMapping("/all-books-in-author/{id}")
    public ResponseEntity<AllBookResponseDto> getBooksByAuthor(@PathVariable UUID id) {
        AllBookResponseDto response = bookService.getBookListByAuthorId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping("/all-books-in-category/{id}")
    public ResponseEntity<AllBookResponseDto> getBookListByCategory(@PathVariable UUID id){
        AllBookResponseDto bookList = bookService.getBookListByCategoryId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookList);
    }

    @GetMapping("/find-by-book-id/{id}")
    public ResponseEntity<BookResponseDto> getById(@PathVariable UUID id){
        BookResponseDto responseDto = bookService.getBook(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping
    public String check(){
        return "salom";
    }
}
