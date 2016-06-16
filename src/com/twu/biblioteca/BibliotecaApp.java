package com.twu.biblioteca;

import com.sun.org.apache.bcel.internal.generic.RET;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {
    public enum bookActions {CHECKOUT, RETURN};
    public enum menuStatuses {VALID, INVALID, QUIT};

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

    static void printNotification(bookActions action, boolean success) {
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

    static void printInvalidOptionMessage(menuStatuses status) {
        if(status == menuStatuses.INVALID)
            System.out.print("Select a valid option!\n");
    }

    static void listBookDetails(ArrayList<Book> bookList) {
        for(Book book: bookList) {
            if (!book.getOnLoan()) printBookDetails(book);
        }
    }

    static void askForBookTitle(){
        System.out.print("Enter book title: ");
    }

    static boolean processBook(ArrayList<Book> bookList, bookActions action) {
        Scanner in = new Scanner(System.in);
        askForBookTitle();
        String input = in.nextLine();

        int bookIdx = findBookByTitle(input, bookList);

        if(bookIdx == -1) {
            printNotification(action, false);
            return false;
        }

        boolean status = false;

        if (action == bookActions.CHECKOUT)
            status = bookList.get(bookIdx).checkoutBook();
        else if (action == bookActions.RETURN)
            status = bookList.get(bookIdx).returnBook();

        printNotification(action, status);

        return status;
    }

    static void showMenuOptions(ArrayList<String> menuOptions) {
        int optNum = 1;
        for(String option: menuOptions) {
            System.out.print(String.format("%d. %s\n", optNum, option));
            optNum++;
        }
    }

    static void showMainMenu(ArrayList<String> menuOptions) {
        System.out.print("Main Menu:\n");
        showMenuOptions(menuOptions);
        System.out.print("Type 'Quit' to exit.\n");
        System.out.print("-----\n");
        System.out.print("Select option, and press Enter: ");
    }

    static menuStatuses checkSelectionValid(ArrayList<String> menuItems, String selection) {
        int selectionValue;
        try{
            selectionValue = Integer.parseInt(selection);
        } catch (NumberFormatException e){
            if(selection.equals("Quit"))
                return menuStatuses.QUIT;
            return menuStatuses.INVALID;
        }
        if (selectionValue > 0 && selectionValue <= menuItems.size())
            return menuStatuses.VALID;
        else
            return menuStatuses.INVALID;
    }

    static int findBookByTitle(String title, ArrayList<Book> bookList){
        int idx = -1;
        for(Book book : bookList){
            idx++;
            if(title.equals(book.getTitle())) {
                return idx;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        // Setup of some "pre-existing" data.
        ArrayList<String> mainMenuItemList = new ArrayList<String>();
        mainMenuItemList.add("List books");
        mainMenuItemList.add("Checkout book");
        mainMenuItemList.add("Return book");

        ArrayList<Book> bookList = new ArrayList<Book>();
        bookList.add(new Book("TW101", "ThoughtWorkers", "2012"));
        bookList.add(new Book("Just in time debug", "Various", "2011"));
        bookList.add(new Book("Let me Fly", "Testers Union", "2016"));
        bookList.add(new Book("TDD is interesting", "John et. al", "2014"));

        Scanner userInput = new Scanner(System.in);
        String selection;
        menuStatuses status;

        // Main Program Flow
        displayWelcomeMessage();

        do{
            showMainMenu(mainMenuItemList);
            selection = userInput.next();

            status = checkSelectionValid(mainMenuItemList, selection);

            printInvalidOptionMessage(status);

            if(status == menuStatuses.VALID){
                int option = Integer.parseInt(selection);
                switch (option){
                    case 1:
                        listBookDetails(bookList);
                        break;
                    case 2:
                        processBook(bookList, bookActions.CHECKOUT);
                        break;
                    case 3:
                        processBook(bookList, bookActions.RETURN);
                        break;
                }
            }

            System.out.println();
        } while (status != menuStatuses.QUIT);
    }
}
