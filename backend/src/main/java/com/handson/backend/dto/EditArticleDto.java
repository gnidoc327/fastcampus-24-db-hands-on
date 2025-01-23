package com.handson.backend.dto;

import lombok.Getter;

import java.util.Optional;

@Getter
public class EditArticleDto {
    String title;
    String content;
    Boolean isHotArticle;
}
