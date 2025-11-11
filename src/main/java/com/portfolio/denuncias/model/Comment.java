package com.portfolio.denuncias.model;

import java.time.LocalDateTime;

public class Comment extends BaseEntity {
    private User author;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment() {}

    public Comment(Long id, User author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public User getAuthor() { return author; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return String.format("%s (%s): %s", author != null ? author.getName() : "Anon", createdAt, content);
    }
}