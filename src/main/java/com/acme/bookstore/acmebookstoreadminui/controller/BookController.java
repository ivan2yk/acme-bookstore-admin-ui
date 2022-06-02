package com.acme.bookstore.acmebookstoreadminui.controller;

import com.acme.bookstore.acmebookstoreadminui.dto.BookDto;
import com.acme.bookstore.acmebookstoreadminui.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/add")
    public String addBook(Model model) {
        BookDto book = new BookDto();
        model.addAttribute("book", book);
        return "addBook";
    }

    @PostMapping(value = "/add")
    public String addBookPost(@ModelAttribute("book") BookDto book, HttpServletRequest request) {
        bookService.create(book);
        MultipartFile bookImage = book.getBookImage();

        try {
            byte[] bytes = bookImage.getBytes();
            String name = book.getId() + ".png";
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream("src/main/resources/static/image/book/" + name));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:bookList";
    }

    @RequestMapping("/bookInfo")
    public String bookInfo(@RequestParam("id") Long id, Model model) {
        BookDto book = bookService.findOne(id);
        model.addAttribute("book", book);
        return "bookInfo";
    }

    @RequestMapping("/updateBook")
    public String updateBook(@RequestParam("id") Long id, Model model) {
        BookDto book = bookService.findOne(id);
        model.addAttribute("book", book);
        return "updateBook";
    }

    @PostMapping(value = "/updateBook")
    public String updateBookPost(@ModelAttribute("book") BookDto book, HttpServletRequest request) {
        bookService.update(book);
        MultipartFile bookImage = book.getBookImage();

        if (!bookImage.isEmpty()) {
            try {
                byte[] bytes = bookImage.getBytes();
                String name = book.getId() + ".png";

                Files.delete(Paths.get("src/main/resources/static/image/book/" + name));

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/book/bookInfo?id=" + book.getId();
    }

    @RequestMapping("/bookList")
    public String bookList(Model model) {
        List<BookDto> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "bookList";

    }

    @PostMapping(value = "/remove")
    public String remove(@ModelAttribute("id") String id, Model model) {
        bookService.removeOne(Long.parseLong(id.substring(8)));
        List<BookDto> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "redirect:/book/bookList";
    }

}
