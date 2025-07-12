// File: app/src/main/java/com/example/caoquangnhat_2123110077/Game.java
// ĐÃ ĐƯỢC CHỈNH SỬA
package com.example.caoquangnhat_2123110077;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class Game implements Serializable {
    private String name;
    private String price;
    private String description;
    private String category;
    private String systemRequirements;
    private float rating;
    private boolean isSelected = true;
    private List<Integer> imageResourceIds;
    private String imageUrl;
    private String developer;
    private String publisher;
    private String releaseDate;

    // === BẮT ĐẦU THÊM TRƯỜNG MỚI CHO TÍNH NĂNG GIẢM GIÁ ===
    private boolean onSale = false;
    private String originalPrice;
    // === KẾT THÚC THÊM TRƯỜNG MỚI ===

    public Game() {
        this.imageResourceIds = new ArrayList<>();
    }

    public Game(String name, String price, List<Integer> imageResourceIds, String category, String description, float rating, String systemRequirements) {
        this.name = name;
        this.price = price;
        this.imageResourceIds = imageResourceIds;
        this.category = category;
        this.description = description;
        this.rating = rating;
        this.systemRequirements = systemRequirements;
    }

    // === GETTERS ===
    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getSystemRequirements() { return systemRequirements; }
    public float getRating() { return rating; }
    public boolean isSelected() { return isSelected; }
    public List<Integer> getImageResourceIds() { return imageResourceIds; }
    public String getImageUrl() { return imageUrl; }
    public String getDeveloper() { return developer; }
    public String getPublisher() { return publisher; }
    public String getReleaseDate() { return releaseDate; }
    public boolean isOnSale() { return onSale; } // Getter mới
    public String getOriginalPrice() { return originalPrice; } // Getter mới

    // === SETTERS ===
    public void setName(String name) { this.name = name; }
    public void setPrice(String price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setSystemRequirements(String systemRequirements) { this.systemRequirements = systemRequirements; }
    public void setRating(float rating) { this.rating = rating; }
    public void setSelected(boolean selected) { isSelected = selected; }
    public void setImageResourceIds(List<Integer> imageResourceIds) { this.imageResourceIds = imageResourceIds; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setDeveloper(String developer) { this.developer = developer; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setOnSale(boolean onSale) { this.onSale = onSale; } // Setter mới
    public void setOriginalPrice(String originalPrice) { this.originalPrice = originalPrice; } // Setter mới

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