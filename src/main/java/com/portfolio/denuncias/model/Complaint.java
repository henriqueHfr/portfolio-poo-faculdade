package com.portfolio.denuncias.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Complaint extends BaseEntity {
    private String title;
    private String description;
    private User reporter;
    private Category category;
    private ComplaintStatus status = ComplaintStatus.OPEN;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    private List<Comment> comments = new ArrayList<>();
    private List<ComplaintHistory> history = new ArrayList<>();

    public Complaint() {}

    public Complaint(Long id, String title, String description, User reporter, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.category = category;
        this.status = ComplaintStatus.OPEN;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public User getReporter() { return reporter; }
    public Category getCategory() { return category; }
    public ComplaintStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<Comment> getComments() { return comments; }
    public List<ComplaintHistory> getHistory() { return history; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setReporter(User reporter) { this.reporter = reporter; }
    public void setCategory(Category category) { this.category = category; }
    public void setStatus(ComplaintStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | %s | %s", id, title, category != null ? category.getName() : "N/C", status, createdAt.toLocalDate());
    }
}
