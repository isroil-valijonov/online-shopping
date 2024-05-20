package com.example.onlineshopping.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {
    private String text;

    private UUID userId;

    private UUID bookId;
}
