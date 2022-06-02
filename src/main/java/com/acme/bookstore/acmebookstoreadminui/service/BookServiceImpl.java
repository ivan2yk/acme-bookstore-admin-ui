package com.acme.bookstore.acmebookstoreadminui.service;

import com.acme.bookstore.acmebookstoreadminui.dto.BookDto;
import com.acme.bookstore.acmebookstoreadminui.dto.BookSaveDto;
import com.acme.bookstore.acmebookstoreadminui.mapper.BookMapper;
import com.acme.bookstore.acmebookstoreadminui.repository.BookRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void create(BookDto book) {
        bookRepository.create(bookMapper.toBookUpdateDto(book));
    }

    @Override
    public BookDto findOne(Long id) {
        return bookRepository.findOne(id);
    }

    @Override
    public void removeOne(long id) {
        bookRepository.removeOne(id);
    }

    @Override
    public void update(BookDto book) {
        bookRepository.update(bookMapper.toBookUpdateDto(book));
    }

}
