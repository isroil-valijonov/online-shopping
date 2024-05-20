package com.example.onlineshopping.book.entity;

import com.example.onlineshopping.category.entity.Category;
import com.example.onlineshopping.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;
    private String name;
    private int year;

    @ManyToMany
    private List<Category> category;

    @ManyToOne
    private User author;

}
