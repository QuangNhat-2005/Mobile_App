// File: app/src/main/java/com/example/caoquangnhat_2123110077/GameApiResponse.java
package com.example.caoquangnhat_2123110077;

import com.google.gson.annotations.SerializedName;

public class GameApiResponse {
    // ... các trường cũ ...
    @SerializedName("id") private int id;
    @SerializedName("title") private String title;
    @SerializedName("thumbnail") private String thumbnail;
    @SerializedName("short_description") private String shortDescription;
    @SerializedName("genre") private String genre;
    @SerializedName("platform") private String platform;

    // === THÊM CÁC TRƯỜNG MỚI ===
    @SerializedName("publisher")
    private String publisher;

    @SerializedName("developer")
    private String developer;

    @SerializedName("release_date")
    private String releaseDate;

    // --- Getters ---
    // ... các getter cũ ...
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getThumbnail() { return thumbnail; }
    public String getShortDescription() { return shortDescription; }
    public String getGenre() { return genre; }
    public String getPlatform() { return platform; }

    // === THÊM GETTER MỚI ===
    public String getPublisher() { return publisher; }
    public String getDeveloper() { return developer; }
    public String getReleaseDate() { return releaseDate; }
}