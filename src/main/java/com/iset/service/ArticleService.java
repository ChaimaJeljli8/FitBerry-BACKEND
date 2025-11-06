/*package com.iset.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iset.DTO.ArticleDTO;
import com.iset.DTO.CreateArticleRequest;
import com.iset.entity.Article;
import com.iset.entity.Nutritioniste;
import com.iset.entity.User;
import com.iset.repository.ArticleRepository;
import com.iset.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
    
	private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, FileStorageService fileStorageService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }
    // Create article
    @Transactional
    public ArticleDTO createArticle(CreateArticleRequest request, Long nutritionnisteId) {
        User nutritionniste = userRepository.findById(nutritionnisteId)
                .orElseThrow(() -> new RuntimeException("Nutritionniste non trouvé"));
        
        if (nutritionniste.getRole() != User.Role.NUTRITIONNISTE) {
            throw new RuntimeException("Seul un nutritionniste peut créer un article");
        }
        
        Article article = new Article();
        article.setTitre(request.getTitre());
        article.setDescription(request.getDescription());
        article.setImageURL(request.getImageURL());
        article.setAuteur(nutritionniste);
        
        Article savedArticle = articleRepository.save(article);
        return convertToDTO(savedArticle);
    }
    
    // Update article
    @Transactional
    public ArticleDTO updateArticle(Long articleId, CreateArticleRequest request, Long nutritionnisteId) {
        User nutritionniste = userRepository.findById(nutritionnisteId)
                .orElseThrow(() -> new RuntimeException("Nutritionniste non trouvé"));
        
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
        
        // Verify that the nutritionist is the author of the article
        if (!article.getAuteur().getId().equals(nutritionnisteId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cet article");
        }
        
        article.setTitre(request.getTitre());
        article.setDescription(request.getDescription());
        article.setImageURL(request.getImageURL());
        
        Article updatedArticle = articleRepository.save(article);
        return convertToDTO(updatedArticle);
    }
    
    // Delete article
    @Transactional
    public void deleteArticle(Long articleId, Long nutritionnisteId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
        
        // Verify that the nutritionist is the author of the article
        if (!article.getAuteur().getId().equals(nutritionnisteId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cet article");
        }
        
        // Delete the image file if it exists
        if (article.getImageURL() != null && !article.getImageURL().isEmpty()) {
            try {
                // Extract filename from URL path
                String filename = article.getImageURL().substring(article.getImageURL().lastIndexOf('/') + 1);
                fileStorageService.deleteFile(filename);
            } catch (Exception ex) {
                // Log error but continue with article deletion
                System.err.println("Error deleting image file: " + ex.getMessage());
            }
        }
        
        articleRepository.delete(article);
    }
    
    // Get all articles
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get article by ID
    public ArticleDTO getArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
        return convertToDTO(article);
    }
    
    // Get all articles by nutritionist
    public List<ArticleDTO> getArticlesByNutritionniste(Long nutritionnisteId) {
        return articleRepository.findByAuteurId(nutritionnisteId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Convert Article to ArticleDTO
    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setTitre(article.getTitre());
        dto.setDescription(article.getDescription());
        dto.setImageURL(article.getImageURL());
        dto.setAuteurId(article.getAuteur().getId());
        
        // If the author is a Nutritionist, retrieve name and first name
        if (article.getAuteur() instanceof Nutritioniste) {
            Nutritioniste nutritioniste = (Nutritioniste) article.getAuteur();
            dto.setAuteurNom(nutritioniste.getNom());
            dto.setAuteurPrenom(nutritioniste.getPrenom());
        }
        
        return dto;
    }
}
*/
package com.iset.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iset.DTO.ArticleDTO;
import com.iset.DTO.CreateArticleRequest;
import com.iset.entity.Article;
import com.iset.entity.Nutritioniste;
import com.iset.entity.User;
import com.iset.repository.ArticleRepository;
import com.iset.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class ArticleService {
    
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, FileStorageService fileStorageService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    // Create article
    @Transactional
    public ArticleDTO createArticle(CreateArticleRequest request, Long nutritionnisteId) {
        User nutritionniste = userRepository.findById(nutritionnisteId)
                .orElseThrow(() -> new RuntimeException("Nutritionniste non trouvé"));
        
        if (nutritionniste.getRole() != User.Role.NUTRITIONNISTE) {
            throw new RuntimeException("Seul un nutritionniste peut créer un article");
        }
        
        Article article = new Article();
        article.setTitre(request.getTitre());
        article.setDescription(request.getDescription());
        article.setImageURL(request.getImageURL());
        article.setAuteur(nutritionniste);
        
        Article savedArticle = articleRepository.save(article);
        return convertToDTO(savedArticle);
    }
    
    // Update article
    @Transactional
    public ArticleDTO updateArticle(Long articleId, CreateArticleRequest request, Long nutritionnisteId) {
        User nutritionniste = userRepository.findById(nutritionnisteId)
                .orElseThrow(() -> new RuntimeException("Nutritionniste non trouvé"));
        
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
        
        // Verify that the nutritionist is the author of the article
        if (!article.getAuteur().getId().equals(nutritionnisteId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cet article");
        }
        
        article.setTitre(request.getTitre());
        article.setDescription(request.getDescription());
        article.setImageURL(request.getImageURL());
        
        Article updatedArticle = articleRepository.save(article);
        return convertToDTO(updatedArticle);
    }
    
    // Delete article (soft delete)
    @Transactional
    public void deleteArticle(Long articleId, Long nutritionnisteId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
        
        // Verify that the nutritionist is the author of the article
        if (!article.getAuteur().getId().equals(nutritionnisteId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cet article");
        }
        
        // Soft delete: mark as deleted instead of removing
        article.setIsDeleted(true);
        article.setDeletedAt(java.time.LocalDateTime.now());
        articleRepository.save(article);
    }
    
    // Get all articles
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .filter(article -> !article.getIsDeleted())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get article by ID
    public ArticleDTO getArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
        
        if (article.getIsDeleted()) {
            throw new RuntimeException("Article non trouvé");
        }
        
        return convertToDTO(article);
    }
    
    // Get all articles by nutritionist
    public List<ArticleDTO> getArticlesByNutritionniste(Long nutritionnisteId) {
        return articleRepository.findByAuteurId(nutritionnisteId).stream()
                .filter(article -> !article.getIsDeleted())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Convert Article to ArticleDTO
    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setTitre(article.getTitre());
        dto.setDescription(article.getDescription());
        dto.setImageURL(article.getImageURL());
        dto.setAuteurId(article.getAuteur().getId());
        
        // If the author is a Nutritionist, retrieve name and first name
        if (article.getAuteur() instanceof Nutritioniste) {
            Nutritioniste nutritioniste = (Nutritioniste) article.getAuteur();
            dto.setAuteurNom(nutritioniste.getNom());
            dto.setAuteurPrenom(nutritioniste.getPrenom());
        }
        
        return dto;
    }
}