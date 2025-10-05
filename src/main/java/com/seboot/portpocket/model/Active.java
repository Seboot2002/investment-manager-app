package com.seboot.portpocket.model;

import java.time.LocalDate;

public record Active(
    String name,
    ActiveType type,
    double initialPrice,
    double currentPrice,
    int quantity,
    LocalDate purchaseDate
) {

    public Active(String name, ActiveType type, double initialPrice, double currentPrice, int quantity) {
        this(name, type, initialPrice, currentPrice, quantity, LocalDate.now());
    }

    public double getTotalValue() {
        return currentPrice * quantity;
    }

    public double getInitialTotalValue() {
        return initialPrice * quantity;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public double getPerformance() {
        if (initialPrice == 0) return 0;
        return ((currentPrice - initialPrice) / initialPrice) * 100.0;
    }
}
