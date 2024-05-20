package com.example.onlineshopping.comment.service;


import com.example.onlineshopping.book.repository.BookRepository;
import com.example.onlineshopping.book.service.BookService;
import com.example.onlineshopping.comment.CommentModelMapper;
import com.example.onlineshopping.comment.dto.AllCommentResponseDto;
import com.example.onlineshopping.comment.dto.CommentCreateDto;
import com.example.onlineshopping.comment.dto.CommentResponseDto;
import com.example.onlineshopping.comment.dto.CommentUpdateDto;
import com.example.onlineshopping.comment.entity.Comment;
import com.example.onlineshopping.comment.repository.CommentRepository;
import com.example.onlineshopping.common.response.CommonResponse;
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
    public CommentResponseDto create(CommentCreateDto createDto) throws Exception {

        try {
            System.out.println(createDto);
            System.out.println(createDto.getBookId());
            System.out.println(createDto.getText());
            System.out.println(createDto.getUserId());


            Comment comment = new Comment();
            comment.setBook(
                    bookService.findByIdBook(createDto.getBookId())
            );
            comment.setUser(
                    userService.findByUserId(createDto.getUserId())
            );
            comment.setText(createDto.getText());
            commentRepository.save(comment);
            System.out.println(mapper.toResponse(comment));
            return mapper.toResponse(comment);
        }
        catch (Exception e){
            throw new Exception("Comment not create:" + e.getMessage(), e);
        }
    }

    public CommentUpdateDto update(UUID commentId, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        comment.setText(commentUpdateDto.getText());
        commentRepository.save(comment);

        return new CommentUpdateDto(comment.getText());
    }

    @Transactional
    public CommonResponse delete(UUID commentId) {


        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

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
    }




    public List<AllCommentResponseDto> allCommentsByUserId(UUID userId) {
        List<Comment> comments = commentRepository.findAllCommentsByUserId(userId);
        return comments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AllCommentResponseDto> allCommentByBookId(UUID bookId){
        List<Comment> comments = commentRepository.getAllCommentsByBookId(bookId);
        return comments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
