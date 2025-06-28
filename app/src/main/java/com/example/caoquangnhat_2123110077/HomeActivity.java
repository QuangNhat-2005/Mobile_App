// File: app/src/main/java/com/example/caoquangnhat_2123110077/HomeActivity.java
package com.example.caoquangnhat_2123110077;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewGames;
    private GameAdapter gameAdapter;
    private List<Game> gameList;
    private ChipGroup chipGroupCategories;
    private EditText searchEditText;
    private String currentCategory = "Tất cả";
    private Set<String> purchasedGameNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ánh xạ các view mới
        Toolbar toolbar = findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        searchEditText = findViewById(R.id.searchEditText);
        ImageButton cartButton = findViewById(R.id.cartButton);
        ImageButton libraryButton = findViewById(R.id.libraryButton);
        ImageButton logoutButton = findViewById(R.id.logoutButton);

        // Xử lý hiệu ứng mờ dần của Toolbar khi cuộn
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            // verticalOffset là 0 khi mở rộng hết cỡ, âm khi cuộn lên
            // Tính toán độ trong suốt (alpha)
            float alpha = (float) Math.abs(verticalOffset) / (float) appBarLayout1.getTotalScrollRange();
            toolbar.setAlpha(alpha);
        });

        setupGameData();
        setupRecyclerView();

        // Xử lý sự kiện cho các nút hành động
        cartButton.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        libraryButton.setOnClickListener(v -> startActivity(new Intent(this, LibraryActivity.class)));
        logoutButton.setOnClickListener(v -> {
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Xử lý sự kiện cho thanh tìm kiếm
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterGames(s.toString(), currentCategory);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Xử lý ChipGroup
        chipGroupCategories = findViewById(R.id.chipGroupCategories);
        // (Giữ nguyên logic ChipGroup của bạn ở đây)
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPurchasedGames();
        if (gameAdapter != null) {
            gameAdapter.updateOwnedGames(purchasedGameNames);
        }
    }

    private void setupRecyclerView() {
        recyclerViewGames = findViewById(R.id.recyclerViewGames);
        recyclerViewGames.setLayoutManager(new LinearLayoutManager(this));
        gameAdapter = new GameAdapter(this, new ArrayList<>(gameList), purchasedGameNames);
        recyclerViewGames.setAdapter(gameAdapter);
    }

    private void loadPurchasedGames() {
        purchasedGameNames = LibraryManager.getPurchasedGameNames(this);
    }

    private void filterGames(String query, String category) {
        List<Game> filteredList = new ArrayList<>();
        for (Game game : gameList) {
            boolean categoryMatch = category.equals("Tất cả") || game.getCategory().equalsIgnoreCase(category);
            boolean searchMatch = query == null || query.isEmpty() || game.getName().toLowerCase().contains(query.toLowerCase());
            if (categoryMatch && searchMatch) {
                filteredList.add(game);
            }
        }
        gameAdapter.filterList(filteredList);
    }

    private void setupGameData() {
        gameList = new ArrayList<>(GameRepository.getAllGames());
        loadPurchasedGames();
    }
}