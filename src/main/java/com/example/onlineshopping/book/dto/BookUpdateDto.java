package com.example.onlineshopping.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDto {
    private String description;
    private String name;
    private int year;
    private List<UUID> category;
    private UUID author;
}
