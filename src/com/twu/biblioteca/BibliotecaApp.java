package com.twu.biblioteca;

import java.util.ArrayList;

public class BibliotecaApp {

    public void displayWelcomeMessage() {
        System.out.printf("Welcome to Biblioteca!\n");
    }

    public void listBooks(ArrayList<String> bookList) {
        for(String title: bookList) {
            System.out.printf("%s\n", title);
        }
    }

    public void printBookDetails(Book book) {
        System.out.printf(book.toString());
    }

    public void listBookDetails(ArrayList<Book> bookList) {
        for(Book book: bookList) {
            printBookDetails(book);
        }
    }

    public void checkoutBook(Book book) {
        boolean checkedout = book.checkoutBook();
        if(checkedout)
            System.out.print("Thank you! Enjoy the book\n");
        else
            System.out.print("That book is not available.\n");
    }

    public void returnBook(Book book) {
        boolean returned = book.returnBook();
        if(returned)
            System.out.print("Thank you for returning the book.\n");
        else
            System.out.print("That is not a valid book to return.\n");
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
