package com.mci.kata.booksreservation;

import java.math.BigDecimal;

public record Book (String uid, String title, String author, int releaseYear) {
    public BigDecimal price() {
        return BigDecimal.valueOf(8.0); // 8 euros
    }
}
