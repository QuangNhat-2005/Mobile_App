package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewGames;
    private GameAdapter gameAdapter;
    private List<Game> allGamesList = new ArrayList<>();
    private ChipGroup chipGroupCategories;
    private EditText searchEditText;
    private String currentCategory = "Tất cả";
    private Set<String> purchasedGameNames;
    public static final String API_TAG = "API_CALL_DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ánh xạ các view
        searchEditText = findViewById(R.id.searchEditText);
        ImageButton cartButton = findViewById(R.id.cartButton);
        ImageButton libraryButton = findViewById(R.id.libraryButton);
        ImageButton accountButton = findViewById(R.id.accountButton);
        // === ĐÃ XÓA ÁNH XẠ CHO LOGOUTBUTTON ===
        chipGroupCategories = findViewById(R.id.chipGroupCategories);
        recyclerViewGames = findViewById(R.id.recyclerViewGames);

        setupRecyclerView();
        fetchGamesFromApi();

        // Thiết lập sự kiện click
        cartButton.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        libraryButton.setOnClickListener(v -> startActivity(new Intent(this, LibraryActivity.class)));
        accountButton.setOnClickListener(v -> startActivity(new Intent(this, AccountActivity.class)));

        // === ĐÃ XÓA SỰ KIỆN CLICK CHO LOGOUTBUTTON ===

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

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String searchQuery = searchEditText.getText().toString().trim();
                filterGames(searchQuery, currentCategory);
                hideKeyboard();
                return true;
            }
            return false;
        });
    }

    // ... các phương thức còn lại của bạn giữ nguyên ...
    @Override
    protected void onResume() {
        super.onResume();
        loadPurchasedGames();
        if (gameAdapter != null) {
            gameAdapter.updateOwnedGames(purchasedGameNames);
        }
    }

    private void fetchGamesFromApi() {
        ApiClient.getApiService().getGames().enqueue(new Callback<List<GameApiResponse>>() {
            @Override
            public void onResponse(Call<List<GameApiResponse>> call, Response<List<GameApiResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allGamesList.clear();
                    Random random = new Random();
                    String[] possiblePrices = {"150,000đ", "250,000đ", "550,000đ", "890,000đ", "1,200,000đ"};
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

                    for (GameApiResponse apiGame : response.body()) {
                        Game game = new Game();
                        game.setName(apiGame.getTitle());
                        game.setDescription(apiGame.getShortDescription());
                        game.setCategory(apiGame.getGenre());
                        game.setSystemRequirements("Platform: " + apiGame.getPlatform());
                        game.setImageUrl(apiGame.getThumbnail());
                        game.setRating(0.0f);
                        game.setDeveloper(apiGame.getDeveloper());
                        game.setPublisher(apiGame.getPublisher());
                        game.setReleaseDate(apiGame.getReleaseDate());

                        if (random.nextDouble() < 0.75) {
                            int priceIndex = random.nextInt(possiblePrices.length);
                            String basePriceStr = possiblePrices[priceIndex];

                            if (random.nextDouble() < 0.25) {
                                try {
                                    double originalPriceValue = Double.parseDouble(basePriceStr.replaceAll("[^\\d]", ""));
                                    double salePriceValue = originalPriceValue * 0.5;
                                    game.setOnSale(true);
                                    game.setOriginalPrice(basePriceStr);
                                    game.setPrice(currencyFormat.format(salePriceValue));
                                } catch (NumberFormatException e) {
                                    game.setPrice(basePriceStr);
                                }
                            } else {
                                game.setPrice(basePriceStr);
                            }
                        } else {
                            game.setPrice("Free to Play");
                        }
                        allGamesList.add(game);
                    }

                    Collections.shuffle(allGamesList);

                    filterGames("", "Tất cả");
                    setupGenreChips(allGamesList);
                } else {
                    Toast.makeText(HomeActivity.this, "Không thể tải dữ liệu game.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<GameApiResponse>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Lỗi kết nối mạng.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        recyclerViewGames.setLayoutManager(new LinearLayoutManager(this));
        loadPurchasedGames();
        gameAdapter = new GameAdapter(this, new ArrayList<>(), purchasedGameNames);
        recyclerViewGames.setAdapter(gameAdapter);
    }

    private void setupGenreChips(List<Game> games) {
        chipGroupCategories.removeAllViews();
        Set<String> genres = new HashSet<>();
        for (Game game : games) {
            genres.add(game.getCategory());
        }
        Chip allChip = createGenreChip("Tất cả");
        allChip.setChecked(true);
        chipGroupCategories.addView(allChip);
        for (String genre : genres) {
            chipGroupCategories.addView(createGenreChip(genre));
        }
        chipGroupCategories.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                allChip.setChecked(true);
                return;
            }
            Chip selectedChip = group.findViewById(checkedIds.get(0));
            if (selectedChip != null) {
                currentCategory = selectedChip.getText().toString();
                filterGames(searchEditText.getText().toString(), currentCategory);
            }
        });
    }

    private Chip createGenreChip(String genre) {
        Chip chip = new Chip(this);
        chip.setText(genre);
        chip.setCheckable(true);
        chip.setClickable(true);
        return chip;
    }

    private void loadPurchasedGames() {
        purchasedGameNames = LibraryManager.getPurchasedGameNames(this);
    }

    private void filterGames(String query, String category) {
        List<Game> filteredList = new ArrayList<>();
        for (Game game : allGamesList) {
            boolean categoryMatch = category.equals("Tất cả") || game.getCategory().equalsIgnoreCase(category);
            boolean searchMatch = query == null || query.isEmpty() || game.getName().toLowerCase().contains(query.toLowerCase().trim());
            if (categoryMatch && searchMatch) {
                filteredList.add(game);
            }
        }
        if (gameAdapter != null) {
            gameAdapter.filterList(filteredList);
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}