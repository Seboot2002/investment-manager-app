package com.seboot.portpocket.model;

public record Active(
    String name,
    ActiveType type,
    double initialPrice,
    double currentPrice,
    int quantity
) {

    public double getTotalValue() {
        return currentPrice * quantity;
    }

    public double getInitialTotalValue() {
        return initialPrice * quantity;
    }

    public double getPerformance() {
        if (initialPrice == 0) return 0;
        return ((currentPrice - initialPrice) / initialPrice) * 100.0;
    }
}
