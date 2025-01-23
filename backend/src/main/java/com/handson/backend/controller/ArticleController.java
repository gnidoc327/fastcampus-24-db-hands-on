package com.handson.backend.controller;

import com.handson.backend.dto.EditArticleDto;
import com.handson.backend.dto.WriteArticleDto;
import com.handson.backend.entity.Article;
import com.handson.backend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;


    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("")
    public ResponseEntity<Article> writeArticle(@RequestBody WriteArticleDto writeArticleDto) {
        return ResponseEntity.ok(articleService.writeArticle(writeArticleDto));
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<Article> editArticle(@PathVariable Long articleId,
                                               @RequestBody EditArticleDto editArticleDto) {
        return ResponseEntity.ok(articleService.editArticle(articleId, editArticleDto));
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok("article is deleted");
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Article> getArticleWithComment(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.getArticle(articleId));
    }
}
