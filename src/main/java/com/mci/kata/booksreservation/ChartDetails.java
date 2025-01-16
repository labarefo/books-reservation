package com.mci.kata.booksreservation;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public record ChartDetails (List<Book> books, BigDecimal totalPrice) {

    @Override
    public List<Book> books() {
        return Collections.unmodifiableList(books);
    }

}
