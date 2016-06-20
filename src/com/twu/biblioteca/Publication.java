package com.twu.biblioteca;

abstract class Publication {
    static final int T_COL = 45;
    static final int C_COL = 45;
    static final int Y_COL = 4;

    String title;
    String creator;
    String year;

    Publication(String title, String creator, String year) {
        this.title = title;
        this.creator = creator;
        this.year = year;
    }

    public abstract String toString();

    String getTitle() {
        return title;
    }

    String getYear() {
        return year;
    }
}
