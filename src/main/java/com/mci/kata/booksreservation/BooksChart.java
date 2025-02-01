package com.mci.kata.booksreservation;

public interface BooksChart {
    void addBook(Book book, int quantity);

    int getSize();

    ChartDetails getChartDetails();
}
