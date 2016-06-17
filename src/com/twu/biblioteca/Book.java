package com.twu.biblioteca;

public class Book extends Publication{
    public Book(String title, String author, String year) {
        super(title, author, year);
    }

    public String toString() {
        return String.format("%-" + T_COL + "s | %-" + C_COL + "s | %-" +
                Y_COL + "s\n", title, creator, year);
    }

    public void printNotification(actions action, boolean success) {
        switch (action) {
            case CHECKOUT:
                if(success) System.out.print("Thank you! Enjoy the book\n");
                else System.out.print("That book is not available.\n");
                break;
            case RETURN:
                if(success) System.out.print("Thank you for returning the book.\n");
                else System.out.print("That is not a valid book to return.\n");
                break;
            default:
                break;
        }
    }

    public String getAuthor() {
        return creator;
    }
}
