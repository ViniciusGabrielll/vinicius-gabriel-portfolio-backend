package com.vinigabriel.portfolio.blog.controller;

import com.vinigabriel.portfolio.blog.dto.ArticleRequest;
import com.vinigabriel.portfolio.blog.model.Article;
import com.vinigabriel.portfolio.blog.service.ArticleService;
import com.vinigabriel.portfolio.blog.service.CloudinaryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    private final CloudinaryService cloudinaryService;

    public ArticleController(
            ArticleService articleService,
            CloudinaryService cloudinaryService
    ) {
        this.articleService = articleService;
        this.cloudinaryService = cloudinaryService;
    }
    @PostMapping
    public ResponseEntity<Article> create(@Valid @RequestBody ArticleRequest request) {
        Article created = articleService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<Article>> findAll() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> findById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> update(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest request
    ) {
        return ResponseEntity.ok(articleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String imageUrl = cloudinaryService.uploadImage(file);

        articleService.updateImage(id, imageUrl);

        return ResponseEntity.ok(imageUrl);
    }

    @PostMapping("/{id}/file")
    public ResponseEntity<String> uploadFile(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String fileUrl = cloudinaryService.uploadFile(file);

        articleService.updateFile(id, fileUrl);

        return ResponseEntity.ok(fileUrl);
    }
}