package com.mci.kata.booksreservation;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BooksChartManager implements BooksReservation {

    private static final double DISCOUNT_5 = 0.05;
    private static final double DISCOUNT_25 = 0.25;

    private final List<Book> chart = new ArrayList<>();

    @Override
    public void addBook(Book book) {
        chart.add(book);
    }

    @Override
    public void addBook(Book book, int quantity) {
        Stream.generate(() -> book)
                .limit(quantity)
                .forEach(this::addBook);
    }

    @Override
    public int getSize() {
        return chart.size();
    }

    @Override
    public ChartDetails getChartDetails() {
        return new ChartDetails(chart, calculateTotalPrice());
    }

    private BigDecimal calculateTotalPrice() {

        Map<String, List<Book>> booksByUid = chart.stream()
                .collect(Collectors.groupingBy(Book::uid, Collectors.toCollection(ArrayList::new)));

        return booksByUid.values()
                .stream()
                .map(this::calculateIdenticalBooksPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateIdenticalBooksPrice(List<Book> books) {
        if (books.size() == 1) {
            return books.getFirst().price();
        }
        BigDecimal netPrice;
        if(books.size() == 2) {
            netPrice = calculateNetPrice(books);
            return applyDiscount(netPrice, DISCOUNT_5);
        }

        netPrice = calculateNetPrice(books);
        // netPrice - 5 * netPrice / 100
        return applyDiscount(netPrice, DISCOUNT_25);
    }

    private static BigDecimal applyDiscount(BigDecimal netPrice, double discount) {
        return netPrice.subtract(netPrice.multiply(BigDecimal.valueOf(discount)));
    }

    private BigDecimal calculateNetPrice(List<Book> books) {
        return books.stream()
                .map(Book::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
