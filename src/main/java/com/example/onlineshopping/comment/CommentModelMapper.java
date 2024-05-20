package com.example.onlineshopping.comment;

import com.example.onlineshopping.comment.dto.CommentCreateDto;
import com.example.onlineshopping.comment.dto.CommentResponseDto;
import com.example.onlineshopping.comment.dto.CommentUpdateDto;
import com.example.onlineshopping.comment.entity.Comment;
import com.example.onlineshopping.common.mapper.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentModelMapper extends GenericMapper<Comment, CommentCreateDto, CommentResponseDto, CommentUpdateDto> {
    private final ModelMapper modelMapper;
    @Override
    public Comment toEntity(CommentCreateDto createDto) {
        return modelMapper.map(createDto, Comment.class);
    }

    @Override
    public CommentResponseDto toResponse(Comment comment) {
        return modelMapper.map(comment, CommentResponseDto.class);
    }

    @Override
    public void toUpdate(CommentUpdateDto commentUpdateDto, Comment comment) {
        modelMapper.map(commentUpdateDto, commentUpdateDto);
    }
}
