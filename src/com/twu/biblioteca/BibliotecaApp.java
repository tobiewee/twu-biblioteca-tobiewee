package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    static void displayWelcomeMessage() {
        System.out.printf("Welcome to Biblioteca!\n");
    }

    static void listBooks(ArrayList<String> bookList) {
        for(String title: bookList) {
            System.out.printf("%s\n", title);
        }
    }

    static void printBookDetails(Book book) {
        System.out.printf(book.toString());
    }

    static void listBookDetails(ArrayList<Book> bookList) {
        for(Book book: bookList) {
            printBookDetails(book);
        }
    }

    static void checkoutBook(Book book) {
        boolean checkedout = book.checkoutBook();
        if(checkedout)
            System.out.print("Thank you! Enjoy the book\n");
        else
            System.out.print("That book is not available.\n");
    }

    static void returnBook(Book book) {
        boolean returned = book.returnBook();
        if(returned)
            System.out.print("Thank you for returning the book.\n");
        else
            System.out.print("That is not a valid book to return.\n");
    }

    static void showMenuOptions(ArrayList<String> menuOptions) {
        int optNum = 1;
        for(String option: menuOptions) {
            System.out.print(String.format("%d. %s\n", optNum, option));
        }
    }

    static void showMainMenu(ArrayList<String> menuOptions) {
        System.out.print("Main Menu:\n");
        showMenuOptions(menuOptions);
        System.out.print("-----\n");
        System.out.print("Select option, and press Enter: ");
    }

    static boolean checkSelectionValid(ArrayList<String> menuItems) {
        Scanner userInput = new Scanner(System.in);
        String selection = userInput.next();
        int selectionValue;
        try{
            selectionValue = Integer.parseInt(selection);
        } catch (NumberFormatException e){
            return false;
        }
        return selectionValue > 0 && selectionValue <= menuItems.size();
    }

    public static void main(String[] args) {
        // Setup of some "pre-existing" data.
        ArrayList<String> mainMenuItemList = new ArrayList<String>();
        mainMenuItemList.add("List books");

        ArrayList<Book> bookList = new ArrayList<Book>();
        bookList.add(new Book("TW101", "ThoughtWorkers", "2012"));
        bookList.add(new Book("Just in time debug", "Various", "2011"));
        bookList.add(new Book("Let me Fly", "Testers Union", "2016"));
        bookList.add(new Book("TDD is interesting", "John et. al", "2014"));

        // Main Program Flow
        displayWelcomeMessage();
        showMainMenu(mainMenuItemList);
        //listBookDetails(bookList);
    }
}
