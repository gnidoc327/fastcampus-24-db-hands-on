package com.handson.backend.service;

import com.handson.backend.entity.Article;
import com.handson.backend.exception.ResourceNotFoundException;
import com.handson.backend.repository.ArticleRepository;
import com.handson.backend.repository.RedisRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ConcurrentLruCache;

@Service
public class RedisCacheService {
    private final RedisRepository redisRepository;
    private final ArticleRepository articleRepository;

    private static final String PREFIX_ARTICLE = "article:";

    public RedisCacheService(ArticleRepository articleRepository, RedisRepository redisRepository) {
        this.articleRepository = articleRepository;
        this.redisRepository = redisRepository;
    }

    public Article getArticle(long articleId) {
        Article article = redisRepository.find(PREFIX_ARTICLE + articleId, Article.class);
        if (article != null) {
            return article;
        }
        return articleRepository.findById(articleId).orElseThrow(() -> new ResourceNotFoundException(articleId + " article not found in redis cache"));
    }

    public void updateArticle(Article article) {
        redisRepository.save(PREFIX_ARTICLE + article.getId(), article);
    }
}
