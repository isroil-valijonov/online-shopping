package com.example.onlineshopping.book.dto;

import com.example.onlineshopping.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookListResponseDto {
    List<BookResponseDto> books;
}
