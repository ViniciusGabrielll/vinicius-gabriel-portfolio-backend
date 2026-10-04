package com.vinigabriel.portfolio.blog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ArticleRequest {

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
    private String title;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String description;

    @Min(value = 1, message = "O tempo de leitura deve ser maior que 0")
    private Integer readingTime;

    private String imageUrl;

    @NotBlank(message = "O conteúdo é obrigatório")
    private String content;

    private List<String> tags;

    private List<RelatedLinkRequest> relatedLinks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(Integer readingTime) {
        this.readingTime = readingTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<RelatedLinkRequest> getRelatedLinks() {
        return relatedLinks;
    }

    public void setRelatedLinks(List<RelatedLinkRequest> relatedLinks) {
        this.relatedLinks = relatedLinks;
    }
}