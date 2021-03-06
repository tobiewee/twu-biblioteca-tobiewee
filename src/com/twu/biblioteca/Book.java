package com.twu.biblioteca;

public class Book {
    public static final int T_COL = 45;
    public static final int A_COL = 45;
    public static final int Y_COL = 4;

    private String title;
    private String author;
    private String year;
    private boolean onLoan;

    public Book(String title, String author, String year) {
        this.title = title;
        this.author = author;
        this.year = year;
        onLoan = false;
    }

    public String toString() {
        return String.format("%-" + T_COL + "s | %-" + A_COL + "s | %-" +
                Y_COL + "s\n", title, author, year);
    }

    public boolean checkoutBook() {
        if(onLoan) return false;
        onLoan = true;
        return onLoan;
    }

    public boolean returnBook() {
        if(!onLoan) return false;
        onLoan = false;
        return !onLoan;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean getOnLoan() {
        return onLoan;
    }
}
