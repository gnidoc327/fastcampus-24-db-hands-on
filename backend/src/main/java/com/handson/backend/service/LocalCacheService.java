package com.handson.backend.service;

import com.handson.backend.entity.Article;
import com.handson.backend.exception.ResourceNotFoundException;
import com.handson.backend.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ConcurrentLruCache;

// Real Subject
@Service
public class LocalCacheService implements ArticleCache {
    private final ConcurrentLruCache<Long, Article> cache;
    private final ArticleRepository articleRepository;

    public LocalCacheService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.cache = new ConcurrentLruCache<>(100, key -> {
            return this.articleRepository
                    .findById(key)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(key + " article not found in local cache"));
        });
    }

    public Article getArticle(long articleId) {
        try {
            return this.cache.get(articleId);
        } catch (ResourceNotFoundException exception) {
            return null;
        }
    }

    public void updateArticle(Article article) {
        this.cache.remove(article.getId());
        this.cache.get(article.getId());
    }
}
