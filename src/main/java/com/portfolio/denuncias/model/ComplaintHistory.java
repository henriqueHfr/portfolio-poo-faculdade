package com.portfolio.denuncias.model;

import java.time.LocalDateTime;

public class ComplaintHistory extends BaseEntity {
    private ComplaintStatus oldStatus;
    private ComplaintStatus newStatus;
    private User changedBy;
    private String note;
    private LocalDateTime changedAt = LocalDateTime.now();

    public ComplaintHistory() {}

    public ComplaintHistory(Long id, ComplaintStatus oldStatus, ComplaintStatus newStatus, User changedBy, String note) {
        this.id = id;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedBy = changedBy;
        this.note = note;
    }

    public ComplaintStatus getOldStatus() { return oldStatus; }
    public ComplaintStatus getNewStatus() { return newStatus; }
    public User getChangedBy() { return changedBy; }
    public String getNote() { return note; }
    public LocalDateTime getChangedAt() { return changedAt; }

    @Override
    public String toString() {
        return String.format("%s -> %s by %s at %s : %s",
                oldStatus, newStatus, changedBy != null ? changedBy.getName() : "Sistema", changedAt, note);
    }
}