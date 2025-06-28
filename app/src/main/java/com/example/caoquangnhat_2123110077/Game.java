// File: app/src/main/java/com/example/caoquangnhat_2123110077/Game.java
package com.example.caoquangnhat_2123110077;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Game implements Serializable {
    private String name;
    private String price;
    private List<Integer> imageResourceIds;
    private String category;
    private String description;
    private float rating;
    private String systemRequirements; // <-- THÊM MỚI
    private boolean isSelected = true;

    // --- CẬP NHẬT CONSTRUCTOR ---
    public Game(String name, String price, List<Integer> imageResourceIds, String category, String description, float rating, String systemRequirements) {
        this.name = name;
        this.price = price;
        this.imageResourceIds = imageResourceIds;
        this.category = category;
        this.description = description;
        this.rating = rating;
        this.systemRequirements = systemRequirements; // <-- THÊM MỚI
    }

    // Getters
    public String getName() { return name; }
    public String getPrice() { return price; }
    public List<Integer> getImageResourceIds() { return imageResourceIds; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public float getRating() { return rating; }
    public String getSystemRequirements() { return systemRequirements; } // <-- THÊM MỚI

    // Getter và Setter cho trạng thái chọn
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(name, game.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}