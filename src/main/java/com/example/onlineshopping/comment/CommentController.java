package com.example.onlineshopping.comment;

import com.example.onlineshopping.category.dto.CategoryResponseDto;
import com.example.onlineshopping.comment.dto.AllCommentResponseDto;
import com.example.onlineshopping.comment.dto.CommentCreateDto;
import com.example.onlineshopping.comment.dto.CommentResponseDto;
import com.example.onlineshopping.comment.dto.CommentUpdateDto;
import com.example.onlineshopping.comment.service.CommentService;
import com.example.onlineshopping.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/create")
    public ResponseEntity<AllCommentResponseDto> create(@RequestBody CommentCreateDto commentCreateDto) throws Exception {

        AllCommentResponseDto responseDto = commentService.create(commentCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentUpdateDto> updateComment(@PathVariable UUID commentId, @RequestBody CommentUpdateDto commentUpdateDto) {
        CommentUpdateDto commentUpdate = commentService.update(commentId, commentUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentUpdate);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<CommonResponse> deleteComment(@PathVariable UUID commentId) {
        CommonResponse delete = commentService.delete(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(delete);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<CategoryResponseDto> findByCommentId(@PathVariable UUID id){
        CategoryResponseDto categoryResponseDto = commentService.commentFindById(id);
        System.out.println(categoryResponseDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseDto);
    }

    @GetMapping("/all-comment-by-user-id/{id}")
    public ResponseEntity<List<AllCommentResponseDto>> allCommentByUserId(@PathVariable UUID id){
        List<AllCommentResponseDto> allComment = commentService.allCommentsByUserId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allComment);
    }

    @GetMapping("/all-comment-by-book-id/{id}")
    public ResponseEntity<List<AllCommentResponseDto>> allCommentByBookId(@PathVariable UUID id){
        List<AllCommentResponseDto> allComment = commentService.allCommentByBookId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allComment);
    }
}
