package com.example.caoquangnhat_2123110077;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LibraryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLibrary;
    private LibraryAdapter libraryAdapter;
    private List<Game> allGamesList; // Danh sách tất cả game trong cửa hàng
    private List<Game> purchasedGamesList; // Danh sách game người dùng đã mua
    private TextView textViewEmptyLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        // --- Thiết lập Toolbar ---
        Toolbar toolbar = findViewById(R.id.toolbarLibrary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // --- Ánh xạ Views ---
        recyclerViewLibrary = findViewById(R.id.recyclerViewLibrary);
        textViewEmptyLibrary = findViewById(R.id.textViewEmptyLibrary);

        // --- Tải và hiển thị dữ liệu ---
        loadAllGamesData(); // Lấy danh sách tất cả game
        loadAndDisplayPurchasedGames();
    }

    // Trong LibraryActivity.java
    private void loadAllGamesData() {
        allGamesList = GameRepository.getAllGames();
    }

    private void loadAndDisplayPurchasedGames() {
        // 1. Lấy danh sách TÊN các game đã mua từ SharedPreferences
        Set<String> purchasedGameNames = LibraryManager.getPurchasedGameNames(this);
        purchasedGamesList = new ArrayList<>();

        // 2. Duyệt qua danh sách TẤT CẢ game
        for (Game game : allGamesList) {
            // 3. Nếu tên game có trong danh sách đã mua, thêm vào list để hiển thị
            if (purchasedGameNames.contains(game.getName())) {
                purchasedGamesList.add(game);
            }
        }

        // 4. Kiểm tra và hiển thị
        if (purchasedGamesList.isEmpty()) {
            textViewEmptyLibrary.setVisibility(View.VISIBLE);
            recyclerViewLibrary.setVisibility(View.GONE);
        } else {
            textViewEmptyLibrary.setVisibility(View.GONE);
            recyclerViewLibrary.setVisibility(View.VISIBLE);
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        recyclerViewLibrary.setLayoutManager(new LinearLayoutManager(this));
        libraryAdapter = new LibraryAdapter(this, purchasedGamesList);
        recyclerViewLibrary.setAdapter(libraryAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}