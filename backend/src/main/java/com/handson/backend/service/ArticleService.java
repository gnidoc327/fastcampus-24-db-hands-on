package com.handson.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.backend.dto.EditArticleDto;
import com.handson.backend.dto.WriteArticleDto;
import com.handson.backend.entity.Article;
import com.handson.backend.exception.ResourceNotFoundException;
import com.handson.backend.repository.ArticleRepository;
import com.handson.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository,
                          ObjectMapper objectMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public Article getArticle(Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        if (article.isEmpty()) {
            throw new ResourceNotFoundException("article not found");
        }
        return article.get();
    }

    @Transactional
    public Article writeArticle(WriteArticleDto dto) {
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        articleRepository.save(article);
        return article;
    }

    @Transactional
    public Article editArticle(Long articleId, EditArticleDto dto) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("article not found"));
        if (dto.getTitle() != null) {
            article.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null) {
            article.setContent(dto.getContent());
        }
        if (dto.getIsHotArticle() != null) {
            article.setIsHotArticle(dto.getIsHotArticle());
        }
        articleRepository.save(article);
        return article;
    }

    @Transactional
    public boolean deleteArticle(Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        if (article.isEmpty()) {
            throw new ResourceNotFoundException("article not found");
        }
        article.get().setIsDeleted(true);
        articleRepository.save(article.get());
        return true;
    }
}
