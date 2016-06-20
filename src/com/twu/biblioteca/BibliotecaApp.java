package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {
    enum menuStatuses {VALID, INVALID, QUIT}

    static void displayWelcomeMessage() {
        System.out.printf("Welcome to Biblioteca!\n");
    }

    static void showMainMenu(ArrayList<String> menuOptions) {
        System.out.print("Main Menu:\n");
        showMenuOptions(menuOptions);
        System.out.print("Type 'Quit' to exit.\n");
        System.out.print("-----\n");
        System.out.print("Select option, and press Enter: ");
    }

    static void showMenuOptions(ArrayList<String> menuOptions) {
        int optNum = 1;
        for(String option: menuOptions) {
            System.out.print(String.format("%d. %s\n", optNum, option));
            optNum++;
        }
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

    static void printInvalidOptionMessage(menuStatuses status) {
        if(status == menuStatuses.INVALID)
            System.out.print("Select a valid option!\n");
    }

    static void askForTitle(){
        System.out.print("Enter title: ");
    }

    private static String getUserInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    static void printPublication (Publication pub) {
        System.out.print(pub.toString());
    }

    static void listPublications(ArrayList<Publication> pubList) {
        for (Publication publication : pubList) {
            printPublication(publication);
        }
    }

    static void listBooks(ArrayList<String> bookList) {
        for(String title: bookList) {
            System.out.printf("%s\n", title);
        }
    }

    static void printNotification(Library.actions action, boolean success, boolean book){
        switch (action) {
            case CHECKOUT:
                if(success){
                    System.out.print("Thank you!");
                    if(book) System.out.print(" Enjoy the book");
                }
                else{
                    System.out.print("That ");
                    if(book) System.out.print("book ");
                    System.out.print("is not available.");
                }
                break;
            case RETURN:
                if(success){
                    System.out.print("Thank you for returning");
                    if(book) System.out.print(" the book.");
                    else System.out.print(".");
                }
                else{
                    System.out.print("That is not a valid");
                    if(book) System.out.print(" book");
                    else System.out.print(" item");
                    System.out.print(" to return.");
                }
                break;
            default:
                break;
        }
        System.out.print('\n');
    }

    public static void main(String[] args) {
        // Setup of some "pre-existing" data.
        ArrayList<String> mainMenuItemList = new ArrayList<String>();
        mainMenuItemList.add("List books");
        mainMenuItemList.add("Checkout book");
        mainMenuItemList.add("Return book");
        mainMenuItemList.add("List movies");
        mainMenuItemList.add("Checkout movie");
        mainMenuItemList.add("View my info");

        Library theLibrary = new Library();

        ArrayList<User> accounts = new ArrayList<User>();
        accounts.add(new User("123-1234", "First User", "firstone@lib.com", "+0129384521"));
        accounts.add(new User("123-1235", "Second User", "scondone@lib.com", "+0129789521"));
        accounts.add(new User("123-1299", "Third User", "thirdone@lib.com", "+0129312321"));

        String selection;
        String input;
        boolean processStatus;
        menuStatuses status;

        String userid;
        String userpw;

        // Main Program Flow
        displayWelcomeMessage();

        do {
            promptLogin();
            userid = getUserInput();
            promptPassword();
            userpw = getUserInput();
        } while(!verifyLogin(accounts, userid, userpw));

        do{
            showMainMenu(mainMenuItemList);
            selection = getUserInput();

            status = checkSelectionValid(mainMenuItemList, selection);

            printInvalidOptionMessage(status);

            if(status == menuStatuses.VALID){
                int option = Integer.parseInt(selection);
                switch (option){
                    case 1:
                        listPublications(theLibrary.getPublicationList(Library.publicationType.BOOK, Library.actions.CHECKOUT));
                        break;
                    case 2:
                        askForTitle();
                        input = getUserInput();
                        processStatus = theLibrary.processPublication(input, Library.actions.CHECKOUT, Library.publicationType.BOOK, userid);
                        printNotification(Library.actions.CHECKOUT, processStatus, true);
                        break;
                    case 3:
                        askForTitle();
                        input = getUserInput();
                        processStatus = theLibrary.processPublication(input, Library.actions.RETURN, Library.publicationType.BOOK, userid);
                        printNotification(Library.actions.RETURN, processStatus, true);

                        break;
                    case 4:
                        listPublications(theLibrary.getPublicationList(Library.publicationType.MOVIE, Library.actions.CHECKOUT));
                        break;
                    case 5:
                        askForTitle();
                        input = getUserInput();
                        processStatus = theLibrary.processPublication(input, Library.actions.CHECKOUT, Library.publicationType.MOVIE, userid);
                        printNotification(Library.actions.CHECKOUT, processStatus, false);
                        break;
                    case 6:
                        User toDisp = getUser(accounts, userid);
                        displayUserDetails(toDisp);
                        break;
                }
            }

            System.out.println();
        } while (status != menuStatuses.QUIT);
    }

    static void promptLogin() {
        System.out.print("Enter login id: ");
    }

    static void promptPassword() {
        System.out.print("Enter password: ");
    }

    static boolean verifyLogin(ArrayList<User> userAccounts, String id, String password) {
        if(!id.matches(User.regexPattern))
            return false;
        User usr = getUser(userAccounts, id);
        if(usr != null) {
            if(usr.verifyPassword(password))
                return true;
        }
        return false;
    }

    static User getUser(ArrayList<User> userAccounts, String id){
        for (User userAccount : userAccounts) {
            if(userAccount.getId().equals(id))
                return userAccount;
        }
        return null;
    }

    static void displayUserDetails(User user) {
        String usrInfo = String.format("Name: %s\nEmail: %s\nPhone: %s\n",
                user.getName(), user.getEmail(), user.getPhoneNum());

        System.out.print(usrInfo);
    }
}
