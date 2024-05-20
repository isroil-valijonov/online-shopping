package com.example.onlineshopping.book.repository;

import com.example.onlineshopping.book.entity.Book;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query(value = "SELECT b.* FROM book b " +
            "JOIN book_category bc ON b.id = bc.book_id " +
            "WHERE bc.category_id = :categoryId", nativeQuery = true)
    List<Book> getBookListByCategoryId(@Param("categoryId") UUID categoryId);


    @Query(nativeQuery = true, value = "select book.* from book where book.author_id=?1")
    List<Book> getBookListByAuthorId(UUID id);
}
