package com.example.caoquangnhat_2123110077;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {

    Button buttonLogout;

    // Khai báo cho Game
    RecyclerView recyclerViewGames;
    GameAdapter gameAdapter;
    List<Game> gameList;

    // Khai báo cho Danh mục
    RecyclerView recyclerViewCategories;
    CategoryAdapter categoryAdapter;
    List<String> categoryList;

    // Khai báo cho Tìm kiếm
    SearchView searchView;
    private String currentCategory = "Tất cả";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // --- Khởi tạo danh sách ---
        gameList = new ArrayList<>();
        categoryList = new ArrayList<>();

        // --- Tạo dữ liệu mẫu ---
        setupGameData();
        setupCategoryData();

        // --- Thiết lập RecyclerView cho Danh mục ---
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(categoryList, this);
        recyclerViewCategories.setAdapter(categoryAdapter);

        // --- Thiết lập RecyclerView cho Game ---
        recyclerViewGames = findViewById(R.id.recyclerViewGames);
        recyclerViewGames.setLayoutManager(new LinearLayoutManager(this));

        // =================== SỬA LỖI Ở ĐÂY ===================
        // Khởi tạo adapter với danh sách game đầy đủ ngay từ đầu.
        gameAdapter = new GameAdapter(this, gameList);
        recyclerViewGames.setAdapter(gameAdapter);
        // =====================================================

        // --- Thiết lập cho SearchView ---
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterGames(newText, currentCategory);
                return true;
            }
        });

        // --- Xử lý nút Đăng xuất ---
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Dòng filterGames("", "Tất cả"); ở cuối không cần thiết nữa vì adapter đã có dữ liệu.
    }

    private void setupCategoryData() {
        categoryList.add("Tất cả");
        categoryList.add("Hành động");
        categoryList.add("Chiến thuật");
        categoryList.add("Nhập vai");
    }

    private void setupGameData() {
        gameList.clear();
        gameList.add(new Game("Elden Ring", "990.000đ",
                Arrays.asList(R.drawable.elden_ring, R.drawable.the_witcher_3),
                "Nhập vai",
                "THE NEW FANTASY ACTION RPG. Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between.",
                4.8f));
        gameList.add(new Game("Baldur's Gate 3", "850.000đ",
                Arrays.asList(R.drawable.baldurs_gate_3),
                "Nhập vai",
                "Gather your party, and return to the Forgotten Realms in a tale of fellowship and betrayal, sacrifice and survival, and the lure of absolute power.",
                4.9f));
        gameList.add(new Game("Cyberpunk 2077", "990.000đ",
                Arrays.asList(R.drawable.cyberpunk_2077, R.drawable.cyberpunk_gp1, R.drawable.cyberpunk_gp2, R.drawable.cyberpunk_gp3, R.drawable.cyberpunk_gp4),
                "Hành động",
                "Cyberpunk 2077 is an open-world, action-adventure story set in Night City, a megalopolis obsessed with power, glamour and body modification. You play as V, a mercenary outlaw going after a one-of-a-kind implant that is the key to immortality.",
                4.2f));
        gameList.add(new Game("Stardew Valley", "165.000đ",
                Arrays.asList(R.drawable.stardew_valley),
                "Chiến thuật",
                "You've inherited your grandfather's old farm plot in Stardew Valley. Armed with hand-me-down tools and a few coins, you set out to begin your new life. Can you learn to live off the land and turn these overgrown fields into a thriving home?",
                4.9f));
        gameList.add(new Game("The Witcher 3", "450.000đ",
                Arrays.asList(R.drawable.the_witcher_3),
                "Nhập vai",
                "As war rages on throughout the Northern Realms, you take on the greatest contract of your life — tracking down the Child of Prophecy, a living weapon that can alter the shape of the world.",
                4.8f));
        gameList.add(new Game("DOOM Eternal", "750.000đ",
                Arrays.asList(R.drawable.doom_eternal),
                "Hành động",
                "Hell’s armies have invaded Earth. Become the Slayer in an epic single-player campaign to conquer demons across dimensions and stop the final destruction of humanity.",
                4.6f));
        gameList.add(new Game("The Last of Us Part II", "1.250.000đ",
                Arrays.asList(R.drawable.the_last_of_us_2),
                "Hành động",
                "Five years after their dangerous journey across the post-pandemic United States, Ellie and Joel have settled down in Jackson, Wyoming. Living amongst a thriving community of survivors has allowed them peace and stability, despite the constant threat of the infected and other, more desperate survivors.",
                4.5f));
    }

    @Override
    public void onCategoryClick(String category) {
        this.currentCategory = category;
        String currentQuery = searchView.getQuery().toString();
        filterGames(currentQuery, category);
    }

    private void filterGames(String query, String category) {
        List<Game> filteredList = new ArrayList<>();
        for (Game game : gameList) {
            boolean categoryMatch = category.equals("Tất cả") || game.getCategory().equalsIgnoreCase(category);
            boolean searchMatch = game.getName().toLowerCase().contains(query.toLowerCase());
            if (categoryMatch && searchMatch) {
                filteredList.add(game);
            }
        }
        gameAdapter.filterList(filteredList);
    }
}