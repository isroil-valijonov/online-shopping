package com.example.onlineshopping.comment.repository;

import com.example.onlineshopping.comment.entity.Comment;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @Query(value = "SELECT c.* FROM comment c WHERE c.user_id = :userId", nativeQuery = true)
    List<Comment> findAllCommentsByUserId(@Param("userId") UUID userId);

    @Query(value = "SELECT c.* FROM comment c WHERE c.book_id = :bookId", nativeQuery = true)
    List<Comment> getAllCommentsByBookId(@Param("bookId") UUID bookId);
}
