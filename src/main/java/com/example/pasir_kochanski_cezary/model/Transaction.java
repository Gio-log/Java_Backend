package com.example.pasir_kochanski_cezary.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TranscriptionType type;

    private String tags;

    private String notes;

    private LocalDateTime timestamp;

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setType(TranscriptionType type) {
        this.type = type;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getNotes() {
        return notes;
    }

    public String getTags() {
        return tags;
    }

    public TranscriptionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public Transaction() {
    }

    public Transaction(LocalDateTime timestamp, String notes, String tags, TranscriptionType type, Double amount) {
        this.timestamp = timestamp;
        this.notes = notes;
        this.tags = tags;
        this.type = type;
        this.amount = amount;
    }
}
