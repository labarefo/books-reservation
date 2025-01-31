package com.mci.kata.booksreservation;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Unit test for simple App.
 */
public class BooksReservationTest {

    @Test
    public void shouldAddOneBookToChart() {
        // Given
        BooksReservation booksReservation = new BooksChartManager();
        Book book = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);

        // When
        booksReservation.addBook(book);

        // Then
        assertThat(booksReservation.getSize()).isEqualTo(1);
    }

    @Test
    public void shouldAddTwoBooksToChart() {
        // Given
        BooksReservation booksReservation = new BooksChartManager();
        Book book1 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);
        Book book2 = new Book("2", "The Lord of the Rings", "J.R.R. Tolkien", 1954);

        // When
        booksReservation.addBook(book1);
        booksReservation.addBook(book2);

        // Then
        assertThat(booksReservation.getSize()).isEqualTo(2);
    }

    @Test
    public void shouldGetShouldChartDetails() {
        // Given
        BooksReservation booksReservation = new BooksChartManager();
        Book book1 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);

        // When
        booksReservation.addBook(book1);

        // Then
        // check chart details
        assertThat(booksReservation.getChartDetails())
                .isNotNull();
        assertThat(booksReservation.getChartDetails().totalPrice())
                .isEqualByComparingTo(BigDecimal.valueOf(8.0));

        assertThat(booksReservation.getChartDetails().books())
                .extracting(Book::title, Book::author, Book::releaseYear)
                .containsExactlyInAnyOrder(
                        tuple("The Hobbit", "J.R.R. Tolkien", 1937)
                );
    }

    @Test
    public void shouldApplyDiscountToTwoIdenticalBooks() {
        // Given
        BooksReservation booksReservation = new BooksChartManager();
        Book book1 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);
        Book book2 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);

        // When
        booksReservation.addBook(book1);
        booksReservation.addBook(book2);

        // Then
        assertThat(booksReservation.getChartDetails())
                .isNotNull();
        assertThat(booksReservation.getChartDetails().totalPrice())
                .isEqualByComparingTo(BigDecimal.valueOf(15.20));

    }

    @Test
    public void shouldApplyDiscountToThreeIdenticalBooks() {
        // Given
        BooksReservation booksReservation = new BooksChartManager();
        Book book1 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);
        Book book2 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);
        Book book3 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);

        // When
        booksReservation.addBook(book1);
        booksReservation.addBook(book2);
        booksReservation.addBook(book3);

        // Then
        assertThat(booksReservation.getChartDetails())
                .isNotNull();
        assertThat(booksReservation.getChartDetails().totalPrice())
                .isEqualByComparingTo(BigDecimal.valueOf(18.0));
    }

    @Test
    public void shouldApplyDiscountToThreeOrMoreIdenticalBooks() {
        // Given
        BooksReservation booksReservation = new BooksChartManager();
        Book book1 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);
        Book book2 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);
        Book book3 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);
        Book book4 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);

        // When
        booksReservation.addBook(book1);
        booksReservation.addBook(book2);
        booksReservation.addBook(book3);
        booksReservation.addBook(book4);

        // Then
        assertThat(booksReservation.getChartDetails())
                .isNotNull();
        assertThat(booksReservation.getChartDetails().totalPrice())
                .isEqualByComparingTo(BigDecimal.valueOf(24.0));
    }

    @Test
    public void shouldApplyDiscount() {
        // Given
        BooksReservation booksReservation = new BooksChartManager();
        Book book1 = new Book("1", "The Hobbit", "J.R.R. Tolkien", 1937);
        Book book2 = new Book("2", "The Lord of the Rings", "J.R.R. Tolkien", 1954);
        Book book3 = new Book("3", "The Silmarillion", "J.R.R. Tolkien", 1977);
        Book book4 = new Book("4", "Unfinished Tales", "J.R.R. Tolkien", 1980);
        Book book5 = new Book("5", "The Children of Húrin", "J.R.R. Tolkien", 2007);

        // When
        booksReservation.addBook(book1, 2);
        booksReservation.addBook(book2, 3);
        booksReservation.addBook(book3, 4);
        booksReservation.addBook(book4);
        booksReservation.addBook(book5);

        // Then
        assertThat(booksReservation.getChartDetails().books().size()).isEqualTo(11);
        assertThat(booksReservation.getChartDetails().totalPrice())
                .isEqualByComparingTo(BigDecimal.valueOf(73.20));
    }

}
