package com.handson.backend.controller;

import com.handson.backend.dto.EditArticleDto;
import com.handson.backend.dto.WriteArticleDto;
import com.handson.backend.entity.Article;
import com.handson.backend.service.ArticleService;
import com.handson.backend.service.LocalCacheService;
import com.handson.backend.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;
//    private final LocalCacheService localCacheService;
    private final RedisCacheService redisCacheService;


    @Autowired
    public ArticleController(ArticleService articleService, LocalCacheService localCacheService, RedisCacheService redisCacheService) {

        this.articleService = articleService;
//        this.localCacheService = localCacheService;
        this.redisCacheService = redisCacheService;
    }

    @PostMapping("")
    public ResponseEntity<Article> writeArticle(@RequestBody WriteArticleDto writeArticleDto) {
        return ResponseEntity.ok(articleService.writeArticle(writeArticleDto));
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<Article> editArticle(@PathVariable Long articleId,
                                               @RequestBody EditArticleDto editArticleDto) {
        Article article = articleService.editArticle(articleId, editArticleDto);
        if (editArticleDto.getIsHotArticle()) {
            redisCacheService.updateArticle(article);
        }
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok("article is deleted");
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Article> getArticleWithComment(@PathVariable Long articleId) {
        return ResponseEntity.ok(redisCacheService.getArticle(articleId));
    }
}
