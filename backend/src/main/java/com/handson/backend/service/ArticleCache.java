package com.handson.backend.service;

import com.handson.backend.entity.Article;

// Subject
public interface ArticleCache {
    Article getArticle(long articleId);
    void updateArticle(Article article);
}
