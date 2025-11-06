package com.iset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.iset.DTO.ArticleDTO;
import com.iset.DTO.CreateArticleRequest;
import com.iset.service.ArticleService;
import com.iset.service.FileStorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;
    private final FileStorageService fileStorageService;

    public ArticleController(ArticleService articleService, FileStorageService fileStorageService) {
        this.articleService = articleService;
        this.fileStorageService = fileStorageService;
    }

    // Create article
    @PostMapping("/nutritionniste/{nutritionnisteId}")
    public ResponseEntity<ArticleDTO> createArticle(
            @RequestBody CreateArticleRequest request,
            @PathVariable Long nutritionnisteId) {
        ArticleDTO article = articleService.createArticle(request, nutritionnisteId);
        return ResponseEntity.ok(article);
    }

    // Update article
    @PutMapping("/{articleId}/nutritionniste/{nutritionnisteId}")
    public ResponseEntity<ArticleDTO> updateArticle(
            @PathVariable Long articleId,
            @RequestBody CreateArticleRequest request,
            @PathVariable Long nutritionnisteId) {
        ArticleDTO article = articleService.updateArticle(articleId, request, nutritionnisteId);
        return ResponseEntity.ok(article);
    }

    // Delete article
    @DeleteMapping("/{articleId}/nutritionniste/{nutritionnisteId}")
    public ResponseEntity<Void> deleteArticle(
            @PathVariable Long articleId,
            @PathVariable Long nutritionnisteId) {
        articleService.deleteArticle(articleId, nutritionnisteId);
        return ResponseEntity.noContent().build();
    }

    // Get all articles
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    // Get article by ID
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long articleId) {
        ArticleDTO article = articleService.getArticleById(articleId);
        return ResponseEntity.ok(article);
    }

    // Get all articles by nutritionniste
    @GetMapping("/nutritionniste/{nutritionnisteId}")
    public ResponseEntity<List<ArticleDTO>> getArticlesByNutritionniste(
            @PathVariable Long nutritionnisteId) {
        List<ArticleDTO> articles = articleService.getArticlesByNutritionniste(nutritionnisteId);
        return ResponseEntity.ok(articles);
    }

    // Upload image only
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String imageUrl = fileStorageService.storeFile(file);
            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", imageUrl);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Upload failed: " + ex.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    
}