package com.vinigabriel.portfolio.blog.repository;

import com.vinigabriel.portfolio.blog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}