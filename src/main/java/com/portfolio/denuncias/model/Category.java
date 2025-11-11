package com.portfolio.denuncias.model;

public class Category extends BaseEntity {
    private String name;
    private String description;
    private boolean active = true;

    public Category() {
    }

    public Category(Long id, String name, String description, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s (%s)", id, name, description, active ? "Ativo" : "Inativo");
    }
}