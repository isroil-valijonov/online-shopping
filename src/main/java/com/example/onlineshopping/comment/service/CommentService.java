package com.example.onlineshopping.comment.service;


import com.example.onlineshopping.book.entity.Book;
import com.example.onlineshopping.book.repository.BookRepository;
import com.example.onlineshopping.book.service.BookService;
import com.example.onlineshopping.category.dto.CategoryResponseDto;
import com.example.onlineshopping.comment.CommentModelMapper;
import com.example.onlineshopping.comment.dto.AllCommentResponseDto;
import com.example.onlineshopping.comment.dto.CommentCreateDto;
import com.example.onlineshopping.comment.dto.CommentResponseDto;
import com.example.onlineshopping.comment.dto.CommentUpdateDto;
import com.example.onlineshopping.comment.entity.Comment;
import com.example.onlineshopping.comment.repository.CommentRepository;
import com.example.onlineshopping.common.exceptions.CustomException;
import com.example.onlineshopping.common.response.CommonResponse;
import com.example.onlineshopping.user.entity.User;
import com.example.onlineshopping.user.repository.UserRepository;
import com.example.onlineshopping.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;
    private final UserService userService;
    private final CommentModelMapper mapper;
    @Transactional
    public AllCommentResponseDto create(CommentCreateDto createDto) throws Exception {

        try {
            Comment comment = new Comment();
            Book book = bookService.findByIdBook(createDto.getBookId());
            comment.setBook(book);
            User user = userService.findByUserId(createDto.getUserId());
            comment.setUser(user);
            comment.setText(createDto.getText());
            commentRepository.save(comment);

            return new AllCommentResponseDto(
                    comment.getId(),
                    comment.getText(),
                    createDto.getUserId(),
                    createDto.getBookId()
            );
        }
        catch (Exception e){
            throw new CustomException("Comment not create:" + e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    public CommentUpdateDto update(UUID commentId, CommentUpdateDto commentUpdateDto) {
        try {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

            comment.setText(commentUpdateDto.getText());
            commentRepository.save(comment);
            return new CommentUpdateDto(comment.getText());
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    @Transactional
    public CommonResponse delete(UUID commentId) {
        try {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new CustomException("Comment not found with id: " + commentId));

            if (comment.getUser() != null) {
                comment.setUser(null);
                commentRepository.save(comment);
            }

            if (comment.getBook() != null) {
                comment.setBook(null);
                commentRepository.save(comment);
            }

            commentRepository.delete(comment);
            return new CommonResponse("Comment deleted successfully", LocalDateTime.now(), HttpStatus.OK.value());
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }
    public CategoryResponseDto commentFindById(UUID id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException("Comment not found with id: " + id));
        return new CategoryResponseDto(
                comment.getId(),
                comment.getText()
        );
    }

    public List<AllCommentResponseDto> allCommentsByUserId(UUID userId) {
        try {
            List<Comment> comments = commentRepository.findAllCommentsByUserId(userId);
            if (comments.isEmpty()){
                throw new CustomException("User not found: " + userId);
            }
            return comments.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    public List<AllCommentResponseDto> allCommentByBookId(UUID bookId){
        try {
            List<Comment> comments = commentRepository.getAllCommentsByBookId(bookId);
            if (comments.isEmpty()){
                throw new CustomException("Book not found: " + bookId);
            }
            return comments.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    private AllCommentResponseDto convertToDto(Comment comment) {
        return new AllCommentResponseDto(
                comment.getId(),
                comment.getText(),
                comment.getUser().getId(),
                comment.getBook().getId()
        );
    }


}
