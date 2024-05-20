package com.example.onlineshopping.comment.dto;

import com.example.onlineshopping.book.entity.Book;
import com.example.onlineshopping.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {

    private UUID id;

    private String text;

    private User user;

    private Book book;
}
