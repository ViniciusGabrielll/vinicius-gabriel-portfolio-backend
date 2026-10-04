package com.vinigabriel.portfolio.blog.service;

import com.vinigabriel.portfolio.blog.dto.ArticleRequest;
import com.vinigabriel.portfolio.blog.dto.RelatedLinkRequest;
import com.vinigabriel.portfolio.blog.exception.ArticleNotFoundException;
import com.vinigabriel.portfolio.blog.model.Article;
import com.vinigabriel.portfolio.blog.model.RelatedLink;
import com.vinigabriel.portfolio.blog.model.Tag;
import com.vinigabriel.portfolio.blog.repository.ArticleRepository;
import com.vinigabriel.portfolio.blog.repository.RelatedLinkRepository;
import com.vinigabriel.portfolio.blog.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final TagRepository tagRepository;

    private final RelatedLinkRepository relatedLinkRepository;

    public ArticleService(
            ArticleRepository articleRepository,
            TagRepository tagRepository,
            RelatedLinkRepository relatedLinkRepository
    ) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.relatedLinkRepository = relatedLinkRepository;
    }

    public Article create(ArticleRequest request) {
        Article article = new Article();

        article.setTitle(request.getTitle());
        article.setDescription(request.getDescription());
        article.setReadingTime(request.getReadingTime());
        article.setImageUrl(request.getImageUrl());
        article.setContent(request.getContent());
        article.setCreatedAt(LocalDateTime.now());
        article.setTags(processTags(request.getTags()));
        article.setRelatedLinks(
                processRelatedLinks(request.getRelatedLinks(), article)
        );

        return articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Artigo não encontrado"));
    }

    public Article update(Long id, ArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Artigo não encontrado"));

        article.setTitle(request.getTitle());
        article.setDescription(request.getDescription());
        article.setReadingTime(request.getReadingTime());
        article.setImageUrl(request.getImageUrl());
        article.setContent(request.getContent());
        article.setTags(processTags(request.getTags()));

        return articleRepository.save(article);
    }

    public void delete(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Artigo não encontrado"));

        articleRepository.delete(article);
    }

    private List<Tag> processTags(List<String> tagNames) {
        if (tagNames == null) {
            return new ArrayList<>();
        }

        if (tagNames.size() > 3) {
            throw new IllegalArgumentException("Um artigo pode ter no máximo 3 tags");
        }

        List<Tag> tags = new ArrayList<>();

        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByNameIgnoreCase(tagName)
                    .orElseGet(() -> {
                        Tag newTag = new Tag();
                        newTag.setName(tagName);
                        return tagRepository.save(newTag);
                    });

            tags.add(tag);
        }

        return tags;
    }

    private List<RelatedLink> processRelatedLinks(
            List<RelatedLinkRequest> linkRequests,
            Article article
    ) {
        if (linkRequests == null) {
            return new ArrayList<>();
        }

        if (linkRequests.size() > 3) {
            throw new IllegalArgumentException(
                    "Um artigo pode ter no máximo 3 links relacionados"
            );
        }

        List<RelatedLink> links = new ArrayList<>();

        for (RelatedLinkRequest request : linkRequests) {
            RelatedLink link = new RelatedLink();

            link.setTitle(request.getTitle());
            link.setUrl(request.getUrl());
            link.setArticle(article);

            links.add(link);
        }

        return links;
    }

    public Article updateImage(Long id, String imageUrl) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Artigo não encontrado"));

        article.setImageUrl(imageUrl);

        return articleRepository.save(article);
    }

    public Article updateFile(Long id, String fileUrl) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Artigo não encontrado"));

        article.setFileUrl(fileUrl);

        return articleRepository.save(article);
    }
}