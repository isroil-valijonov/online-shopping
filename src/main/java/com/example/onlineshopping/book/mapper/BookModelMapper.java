package com.example.onlineshopping.book.mapper;


import com.example.onlineshopping.book.dto.BookCreateDto;
import com.example.onlineshopping.book.dto.AllBookResponseDto;
import com.example.onlineshopping.book.dto.BookUpdateDto;
import com.example.onlineshopping.book.entity.Book;
import com.example.onlineshopping.common.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookModelMapper extends GenericMapper <Book, BookCreateDto, AllBookResponseDto, BookUpdateDto>{
    private final ModelMapper modelMapper;
    @Override
    public Book toEntity(BookCreateDto bookCreateDto) {
        return modelMapper.map(bookCreateDto, Book.class);
    }

    @Override
    public AllBookResponseDto toResponse(Book book) {
        AllBookResponseDto bookResponseDto = new AllBookResponseDto();
        bookResponseDto.setId(book.getId());
        bookResponseDto.setName(book.getName());
        bookResponseDto.setDescription(book.getDescription());
        bookResponseDto.setYear(book.getYear());
        bookResponseDto.setCategory(book.getCategory());
        bookResponseDto.setAuthors(book.getAuthor());
        return bookResponseDto;
    }

    @Override
    public void toUpdate(BookUpdateDto bookUpdateDto, Book book) {
        modelMapper.map(bookUpdateDto, book);
    }
}
