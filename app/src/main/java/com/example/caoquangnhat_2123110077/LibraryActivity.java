// File: app/src/main/java/com/example/caoquangnhat_2123110077/LibraryActivity.java
package com.example.caoquangnhat_2123110077;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLibrary;
    private LibraryAdapter adapter;
    private List<Game> libraryGames;
    private TextView textEmptyLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewLibrary = findViewById(R.id.recyclerViewLibrary);
        textEmptyLibrary = findViewById(R.id.text_empty_library);

        // Lấy danh sách game từ LibraryManager đã được nâng cấp
        libraryGames = LibraryManager.getLibraryGames(this);

        // Kiểm tra xem thư viện có rỗng không
        if (libraryGames.isEmpty()) {
            recyclerViewLibrary.setVisibility(View.GONE);
            textEmptyLibrary.setVisibility(View.VISIBLE);
        } else {
            recyclerViewLibrary.setVisibility(View.VISIBLE);
            textEmptyLibrary.setVisibility(View.GONE);
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        recyclerViewLibrary.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LibraryAdapter(this, libraryGames);
        recyclerViewLibrary.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}