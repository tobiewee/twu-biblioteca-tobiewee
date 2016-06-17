package com.twu.biblioteca;

public class Book extends Publication{
    public static final int T_COL = 45;
    public static final int A_COL = 45;
    public static final int Y_COL = 4;

    public Book(String title, String author, String year) {
        super(title, author, year);
    }

    public String toString() {
        return String.format("%-" + T_COL + "s | %-" + A_COL + "s | %-" +
                Y_COL + "s\n", title, creator, year);
    }

    public String getAuthor() {
        return creator;
    }
}
