package com.twu.biblioteca;

public class Book {
    public static final int T_COL = 45;
    public static final int A_COL = 45;
    public static final int Y_COL = 4;

    public String title;
    public String author;
    public String year;

    public Book(String title, String author, String year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String toString() {
        return String.format("%-" + T_COL + "." + T_COL + "s | %-" +
                A_COL + "." + A_COL + "s | %-" +
                Y_COL + "." + Y_COL + "s\n", title, author, year);
    }
}
