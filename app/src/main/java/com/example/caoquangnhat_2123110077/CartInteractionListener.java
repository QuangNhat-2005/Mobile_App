package com.example.caoquangnhat_2123110077;

public interface CartInteractionListener {
    void onItemDeleted(Game game, int position);
    void onItemSelectionChanged(Game game, boolean isSelected);
}