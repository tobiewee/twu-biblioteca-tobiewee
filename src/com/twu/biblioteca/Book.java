package com.twu.biblioteca;

class Book extends Publication{
    Book(String title, String author, String year) {
        super(title, author, year);
    }

    public String toString() {
        return String.format("%-" + T_COL + "s | %-" + C_COL + "s | %-" +
                Y_COL + "s\n", title, creator, year);
    }

    String getAuthor() {
        return creator;
    }
}
