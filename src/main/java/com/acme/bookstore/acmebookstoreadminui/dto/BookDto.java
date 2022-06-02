package com.acme.bookstore.acmebookstoreadminui.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString(of = {"id", "title", "author", "category", "language"})
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String publicationDate;
    private String language;
    private String category;
    private int numberOfPages;
    private String format;
    private int isbn;
    private double shippingWeight;
    private double listPrice;
    private double ourPrice;
    private boolean active;
    private String description;
    private int inStockNumber;
    private MultipartFile bookImage;

}
