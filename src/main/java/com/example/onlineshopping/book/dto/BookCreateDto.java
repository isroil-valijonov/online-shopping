package com.example.onlineshopping.book.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {
    private String description;
    @NotBlank(message = "name is invalid")
    private String name;
    private int year;
    private List<UUID> category;
    private UUID author;
}
