package com.vinigabriel.portfolio.blog.repository;

import com.vinigabriel.portfolio.blog.model.RelatedLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatedLinkRepository extends JpaRepository<RelatedLink, Long> {
}