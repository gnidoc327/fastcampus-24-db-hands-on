package com.handson.backend.controller;

import com.handson.backend.dto.EditArticleDto;
import com.handson.backend.dto.WriteArticleDto;
import com.handson.backend.entity.Article;
import com.handson.backend.service.ArticleProxyService;
import com.handson.backend.service.LocalCacheService;
import com.handson.backend.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleProxyService articleProxyService;

    @Autowired
    public ArticleController(ArticleProxyService articleProxyService) {

        this.articleProxyService = articleProxyService;
    }

    @PostMapping("")
    public ResponseEntity<Article> writeArticle(@RequestBody WriteArticleDto writeArticleDto) {
        return ResponseEntity.ok(articleProxyService.writeArticle(writeArticleDto));
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<Article> editArticle(@PathVariable Long articleId,
                                               @RequestBody EditArticleDto editArticleDto) {
        Article article = articleProxyService.editArticle(articleId, editArticleDto);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long articleId) {
        articleProxyService.deleteArticle(articleId);
        return ResponseEntity.ok("article is deleted");
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Article> getArticleWithComment(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleProxyService.getArticle(articleId));
    }
}
