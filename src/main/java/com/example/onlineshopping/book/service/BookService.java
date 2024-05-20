package com.example.onlineshopping.book.service;

import com.example.onlineshopping.book.dto.AllBookResponseDto;
import com.example.onlineshopping.book.dto.BookCreateDto;
import com.example.onlineshopping.book.dto.BookResponseDto;
import com.example.onlineshopping.book.dto.BookUpdateDto;
import com.example.onlineshopping.book.entity.Book;
import com.example.onlineshopping.book.mapper.BookModelMapper;
import com.example.onlineshopping.book.repository.BookRepository;
import com.example.onlineshopping.category.entity.Category;
import com.example.onlineshopping.category.repository.CategoryRepository;
import com.example.onlineshopping.common.response.CommonResponse;
import com.example.onlineshopping.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final BookModelMapper mapper;

    public BookResponseDto create(BookCreateDto bookCreateDto) throws Exception {
        try {
            Book book = new Book();
            book.setName(bookCreateDto.getName());
            book.setAuthor(userService.findByUserId(bookCreateDto.getAuthor()));
            book.setDescription(bookCreateDto.getDescription());
            book.setYear(bookCreateDto.getYear());

            if (bookCreateDto.getCategory() != null) {
                List<Category> categories = bookCreateDto.getCategory().stream()
                        .map(id -> categoryRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Category not found")))
                        .collect(Collectors.toList());
                book.setCategory(categories);
            }

            Book savedBook = bookRepository.save(book);
            return mapper.toResponse(savedBook);
        } catch (Exception e) {
            throw new Exception("Error creating book: " + e.getMessage(), e);
        }
    }

    public BookResponseDto update(UUID id, BookUpdateDto bookUpdateDto){

        Book byIdBook = findByIdBook(id);
        if (byIdBook == null) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }

        byIdBook.setName(bookUpdateDto.getName());
        byIdBook.setYear(bookUpdateDto.getYear());
        byIdBook.setDescription(bookUpdateDto.getDescription());
        byIdBook.setAuthor(userService.findByUserId(bookUpdateDto.getAuthor()));

        List<Category> categories = bookUpdateDto.getCategory().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId)))
                .collect(Collectors.toList());
        byIdBook.setCategory(categories);

        bookRepository.save(byIdBook);

        return mapper.toResponse(byIdBook);

    }

    public CommonResponse delete(UUID id){
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id: " + id));

        bookRepository.delete(book);

        return new CommonResponse("Book was deleted successfully", LocalDateTime.now(), HttpStatus.OK.value());

    }

    public BookResponseDto getBook(UUID id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public AllBookResponseDto getBookListByCategoryId(UUID id){
        List<Book> bookList = bookRepository.getBookListByCategoryId(id);
        return new AllBookResponseDto(bookList);
    }

    public AllBookResponseDto getBookListByAuthorId(UUID id){
        List<Book> books = bookRepository.getBookListByAuthorId(id);
        return new AllBookResponseDto(books);
    }

    public Book findByIdBook(UUID id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElseThrow(() -> new RuntimeException("Book not found"));
    }

}
