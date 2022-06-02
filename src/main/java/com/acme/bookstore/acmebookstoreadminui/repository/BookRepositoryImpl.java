package com.acme.bookstore.acmebookstoreadminui.repository;

import com.acme.bookstore.acmebookstoreadminui.dto.BookDto;
import com.acme.bookstore.acmebookstoreadminui.dto.BookSaveDto;
import com.acme.bookstore.acmebookstoreadminui.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class BookRepositoryImpl implements BookRepository {

    @Value("${acme.bookstore.product.service.url}")
    private String productServiceUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public List<BookDto> findAll() {
        HttpRequest request = HttpRequest.newBuilder(URI.create(productServiceUrl)).GET().build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            String jsonBody = httpResponse.body();

            return JsonUtil.convertTo(jsonBody, new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e.getCause());
        }
        return null;
    }

    @Override
    public BookDto findOne(Long id) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(String.format("%s/%s", productServiceUrl, id))).GET().build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            String jsonBody = httpResponse.body();
            return JsonUtil.convertTo(jsonBody, BookDto.class);
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e.getCause());
        }
        return null;
    }

    @Override
    public void removeOne(long id) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(String.format("%s/%s", productServiceUrl, id))).DELETE().build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void update(BookSaveDto book) {
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(JsonUtil.writeJson(book));
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(String.format("%s/%s", productServiceUrl, book.getId()))).PUT(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();

        try {
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void create(BookSaveDto book) {
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(JsonUtil.writeJson(book));
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(productServiceUrl)).POST(bodyPublisher)
                .header("Content-Type", "application/json")
                .build();

        try {
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

}
