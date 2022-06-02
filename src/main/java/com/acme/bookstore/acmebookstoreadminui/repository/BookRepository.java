package com.acme.bookstore.acmebookstoreadminui.repository;

import com.acme.bookstore.acmebookstoreadminui.dto.BookDto;
import com.acme.bookstore.acmebookstoreadminui.dto.BookSaveDto;

import java.util.List;

public interface BookRepository {

    List<BookDto> findAll();

    BookDto findOne(Long id);

    void removeOne(long id);

    void update(BookSaveDto book);

    void create(BookSaveDto book);

}
