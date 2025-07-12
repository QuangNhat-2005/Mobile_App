// File: app/src/main/java/com/example/caoquangnhat_2123110077/Transaction.java
// FILE MỚI
package com.example.caoquangnhat_2123110077;

import java.io.Serializable;
import java.util.List;

public class Transaction implements Serializable {
    private final long transactionId;
    private final long transactionDate;
    private final List<Game> purchasedGames;
    private final String totalAmount;
    private final String paymentMethod;

    public Transaction(long transactionId, long transactionDate, List<Game> purchasedGames, String totalAmount, String paymentMethod) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.purchasedGames = purchasedGames;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    // Getters
    public long getTransactionId() {
        return transactionId;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public List<Game> getPurchasedGames() {
        return purchasedGames;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}