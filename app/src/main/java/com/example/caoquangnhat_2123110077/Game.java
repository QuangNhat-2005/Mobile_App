package com.example.caoquangnhat_2123110077;

import java.util.List;

// KHÔNG CẦN "implements Serializable" nữa
public class Game {
    private String name;
    private String price;
    private List<Integer> imageResourceIds;
    private String category;
    private String description;
    private float rating;

    public Game(String name, String price, List<Integer> imageResourceIds, String category, String description, float rating) {
        this.name = name;
        this.price = price;
        this.imageResourceIds = imageResourceIds;
        this.category = category;
        this.description = description;
        this.rating = rating;
    }

    // Getters
    public String getName() { return name; }
    public String getPrice() { return price; }
    public List<Integer> getImageResourceIds() { return imageResourceIds; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public float getRating() { return rating; }
}