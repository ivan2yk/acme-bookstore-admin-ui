package com.acme.bookstore.acmebookstoreadminui.mapper;

import com.acme.bookstore.acmebookstoreadminui.dto.BookDto;
import com.acme.bookstore.acmebookstoreadminui.dto.BookSaveDto;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {

    BookSaveDto toBookUpdateDto(BookDto bookDto);

}
