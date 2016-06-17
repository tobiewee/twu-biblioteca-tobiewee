package com.twu.biblioteca;

public class Book extends Publication{
    public Book(String title, String author, String year) {
        super(title, author, year);
    }

    public String toString() {
        return String.format("%-" + T_COL + "s | %-" + C_COL + "s | %-" +
                Y_COL + "s\n", title, creator, year);
    }

    public String getAuthor() {
        return creator;
    }
}
