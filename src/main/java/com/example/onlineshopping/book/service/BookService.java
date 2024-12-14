package com.example.onlineshopping.book.service;

import com.example.onlineshopping.book.dto.*;
import com.example.onlineshopping.book.entity.Book;
import com.example.onlineshopping.book.mapper.BookModelMapper;
import com.example.onlineshopping.book.repository.BookRepository;
import com.example.onlineshopping.category.entity.Category;
import com.example.onlineshopping.category.repository.CategoryRepository;
import com.example.onlineshopping.common.exceptions.CustomException;
import com.example.onlineshopping.common.response.CommonResponse;
import com.example.onlineshopping.user.repository.UserRepository;
import com.example.onlineshopping.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final BookModelMapper mapper;

    public AllBookResponseDto create(BookCreateDto bookCreateDto) throws Exception {
        try {
            Book book = new Book();
            book.setName(bookCreateDto.getName());
            book.setAuthor(userService.findByUserId(bookCreateDto.getAuthor()));
            book.setDescription(bookCreateDto.getDescription());
            book.setYear(bookCreateDto.getYear());

            if (bookCreateDto.getCategory().isEmpty()) {
                book.setCategory(null);
            }
            List<Category> categories = bookCreateDto.getCategory().stream()
                    .map(id -> categoryRepository.findById(id)
                            .orElseThrow(() -> new CustomException("Category not found: " + id, HttpStatus.NOT_FOUND.value())))
                    .collect(Collectors.toList());
            book.setCategory(categories);
            Book savedBook = bookRepository.save(book);
            return mapper.toResponse(savedBook);
        } catch (Exception e) {
            throw new Exception("Error creating book: " + e.getMessage(), e);
        }
    }

    public AllBookResponseDto update(UUID id, BookUpdateDto bookUpdateDto) {
        try {
            Book byIdBook = findByIdBook(id);
            byIdBook.setName(bookUpdateDto.getName());
            byIdBook.setYear(bookUpdateDto.getYear());
            byIdBook.setDescription(bookUpdateDto.getDescription());
            byIdBook.setAuthor(userService.findByUserId(bookUpdateDto.getAuthor()));

            if (bookUpdateDto.getCategory().isEmpty()){
                byIdBook.setCategory(null);
            }
            List<Category> categories = bookUpdateDto.getCategory().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new CustomException("Category not found with id: " + categoryId, HttpStatus.NOT_FOUND.value())))
                    .collect(Collectors.toList());
            byIdBook.setCategory(categories);
            bookRepository.save(byIdBook);
            return mapper.toResponse(byIdBook);
        } catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    public CommonResponse delete(UUID id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(
                    () -> new CustomException("Book not found: " + id, HttpStatus.NOT_FOUND.value()));

            if (book.getAuthor() != null) {
                book.setAuthor(null);
                book.setCategory(null);
                bookRepository.save(book);
            }

            bookRepository.deleteById(id);
            return new CommonResponse("Book was deleted successfully", LocalDateTime.now(), HttpStatus.OK.value());
        } catch (Exception e) {
            return new CommonResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        }

    }

    public AllBookResponseDto getBookWithAuthorAndCategory(UUID id) {
        try {
            Book book = findByIdBook(id);
            return new AllBookResponseDto(
                    book.getId(),
                    book.getName(),
                    book.getDescription(),
                    book.getYear(),
                    book.getCategory(),
                    book.getAuthor()
            );
        } catch (Exception e){
            throw  new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }
    public BookResponseDto getBook(UUID id){
        try {
            Book book = findByIdBook(id);
            return new BookResponseDto(
                    book.getId(),
                    book.getName(),
                    book.getDescription(),
                    book.getYear()
            );
        } catch (Exception e){
            throw  new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    public BookListResponseDto getBookListByCategoryId(UUID id) {
        try {
            if (categoryRepository.existsById(id)){
                List<Book> bookList = bookRepository.getBookListByCategoryId(id);
                List<BookResponseDto> bookResponseDto = bookList.stream()
                        .map(book -> new BookResponseDto(book.getId(), book.getDescription(), book.getName(), book.getYear()))
                        .collect(Collectors.toList());
                return new BookListResponseDto(bookResponseDto);
            }
            throw new CustomException("Category not found: " + id, HttpStatus.NOT_FOUND.value());
        } catch (Exception e){
            throw new CustomException(e.getMessage(),HttpStatus.BAD_REQUEST.value());
        }
    }

    public BookListResponseDto getBookListByAuthorId(UUID id) {
        try {
            if (userRepository.existsById(id)) {
                List<Book> bookList = bookRepository.getBookListByAuthorId(id);
                List<BookResponseDto> bookResponseDto = bookList.stream()
                        .map(book -> new BookResponseDto(book.getId(), book.getName(), book.getDescription(), book.getYear()))
                        .collect(Collectors.toList());
                return new BookListResponseDto(bookResponseDto);
            }
            throw new CustomException("Author not found: "+ id, HttpStatus.NOT_FOUND.value());
        } catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    public Book findByIdBook(UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElseThrow(() -> new CustomException("Book not found: "+ id, HttpStatus.NOT_FOUND.value()));
    }

}
