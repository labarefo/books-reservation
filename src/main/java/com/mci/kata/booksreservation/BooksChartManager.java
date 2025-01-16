package com.mci.kata.booksreservation;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BooksChartManager implements BooksReservation {

    private static final BigDecimal DISCOUNT_5 = BigDecimal.valueOf(5);
    private static final BigDecimal DISCOUNT_25 = BigDecimal.valueOf(25);

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
            return books.iterator().next().price();
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

    private static BigDecimal applyDiscount(BigDecimal netPrice, BigDecimal discount) {
        return netPrice.subtract(discount.multiply(netPrice).divide(BigDecimal.valueOf(100), RoundingMode.DOWN));
    }

    private BigDecimal calculateNetPrice(List<Book> books) {
        return books.stream()
                .map(Book::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
