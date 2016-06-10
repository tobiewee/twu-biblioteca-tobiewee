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

    public void showMainMenu() {
        System.out.print("Main Menu:\n");
        System.out.print("1. List Books\n");
        System.out.print("Choice: ");
    }

    public void invalidMenuOption() {
        System.out.print("Select a valid option!\n");
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
