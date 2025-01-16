package com.mci.kata.booksreservation;

import java.util.List;

public interface BooksReservation {
    void addBook(Book book);
    void addBook(Book book, int quantity);

    int getSize();

    ChartDetails getChartDetails();
}
