// File: app/src/main/java/com/example/caoquangnhat_2123110077/TransactionHistoryAdapter.java
// FILE MỚI
package com.example.caoquangnhat_2123110077;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewHolder> {

    private final List<Transaction> transactionList;

    public TransactionHistoryAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.transactionId.setText("Mã GD: " + transaction.getTransactionId());
        holder.totalAmount.setText("Tổng tiền: " + transaction.getTotalAmount());
        holder.paymentMethod.setText(transaction.getPaymentMethod());

        // Format date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        holder.transactionDate.setText(sdf.format(new Date(transaction.getTransactionDate())));

        // Build games list string
        StringBuilder gamesBuilder = new StringBuilder();
        for (Game game : transaction.getPurchasedGames()) {
            gamesBuilder.append("- ").append(game.getName()).append("\n");
        }
        // Remove last newline
        if (gamesBuilder.length() > 0) {
            gamesBuilder.setLength(gamesBuilder.length() - 1);
        }
        holder.gamesList.setText(gamesBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView transactionId, transactionDate, gamesList, totalAmount, paymentMethod;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionId = itemView.findViewById(R.id.text_transaction_id);
            transactionDate = itemView.findViewById(R.id.text_transaction_date);
            gamesList = itemView.findViewById(R.id.text_transaction_games);
            totalAmount = itemView.findViewById(R.id.text_transaction_total);
            paymentMethod = itemView.findViewById(R.id.text_transaction_payment_method);
        }
    }
}