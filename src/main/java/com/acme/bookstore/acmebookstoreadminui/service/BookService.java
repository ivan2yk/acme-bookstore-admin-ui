package com.acme.bookstore.acmebookstoreadminui.service;

import com.acme.bookstore.acmebookstoreadminui.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAll();

    void create(BookDto book);

    BookDto findOne(Long id);

    void removeOne(long id);

    void update(BookDto book);

}
