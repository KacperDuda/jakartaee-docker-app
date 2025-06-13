package com.example.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Invoice {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
    private List<InvoiceItem> items = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    // Optional: method to add an item
    public void addItem(InvoiceItem item) {
        this.items.add(item);
    }
}