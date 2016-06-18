package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {
    public enum menuStatuses {VALID, INVALID, QUIT}

    static void listBooks(ArrayList<String> bookList) {
        for(String title: bookList) {
            System.out.printf("%s\n", title);
        }
    }

    static void displayWelcomeMessage() {
        System.out.printf("Welcome to Biblioteca!\n");
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

    static void showMainMenu(ArrayList<String> menuOptions) {
        System.out.print("Main Menu:\n");
        showMenuOptions(menuOptions);
        System.out.print("Type 'Quit' to exit.\n");
        System.out.print("-----\n");
        System.out.print("Select option, and press Enter: ");
    }

    static void listPublicationDetails(ArrayList<Publication> pubList) {
        for(Publication pub: pubList) {
            if (!pub.getOnLoan()) printPublicationDetails(pub);
        }
    }

    static void printPublicationDetails(Publication pub) {
        System.out.printf(pub.toString());
    }

    static void printInvalidOptionMessage(menuStatuses status) {
        if(status == menuStatuses.INVALID)
            System.out.print("Select a valid option!\n");
    }

    static void askForTitle(){
        System.out.print("Enter title: ");
    }

    static int findPublicationByTitle(String title, ArrayList<Publication> pubList){
        int idx = -1;
        for(Publication pub : pubList){
            idx++;
            if(title.equals(pub.getTitle())) {
                return idx;
            }
        }
        return -1;
    }

    static boolean processPublication(ArrayList<Publication> pubList, Publication.actions action) {
        Scanner in = new Scanner(System.in);
        askForTitle();
        String input = in.nextLine();

        int index = findPublicationByTitle(input, pubList);

        if(index == -1) {
            pubList.get(0).printNotification(action, false);
            return false;
        }

        boolean status = pubList.get(index).updateStatus(action);

        pubList.get(index).printNotification(action, status);

        return status;
    }

    static void promptLogin() {
        System.out.print("Enter login id: ");
    }

    static void promptPassword() {
        System.out.print("Enter password: ");
    }

    static boolean verifyLogin(ArrayList<User> userAccounts, String id, String password) {
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

    public static void main(String[] args) {
        // Setup of some "pre-existing" data.
        ArrayList<String> mainMenuItemList = new ArrayList<String>();
        mainMenuItemList.add("List books");
        mainMenuItemList.add("Checkout book");
        mainMenuItemList.add("Return book");
        mainMenuItemList.add("List movies");
        mainMenuItemList.add("Checkout movie");
        mainMenuItemList.add("View my info");

        ArrayList<Publication> bookList = new ArrayList<Publication>();
        bookList.add(new Book("TW101", "ThoughtWorkers", "2012"));
        bookList.add(new Book("Just in time debug", "Various", "2011"));
        bookList.add(new Book("Let me Fly", "Testers Union", "2016"));
        bookList.add(new Book("TDD is interesting", "John et. al", "2014"));

        ArrayList<Publication> movieList = new ArrayList<Publication>();
        movieList.add(new Movie("ThoughtWorks History", "Infant Thomas", "2016", "10"));
        movieList.add(new Movie("ThoughtWorks Staffing", "Anshul", "2016", ""));
        movieList.add(new Movie("ThoughtWorks Fun & Games", "TWers", "2016", "unrated"));

        ArrayList<User> accounts = new ArrayList<User>();
        accounts.add(new User("123-1234", "First User", "firstone@lib.com", "+0129384521"));
        accounts.add(new User("123-1235", "Second User", "scondone@lib.com", "+0129789521"));
        accounts.add(new User("123-1299", "Third User", "thirdone@lib.com", "+0129312321"));

        Scanner userInput = new Scanner(System.in);
        String selection;
        menuStatuses status;

        String userid;
        String userpw;

        // Main Program Flow
        displayWelcomeMessage();

        do {
            promptLogin();
            userid = userInput.nextLine();
            promptPassword();
            userpw = userInput.nextLine();
        } while(!verifyLogin(accounts, userid, userpw));

        do{
            showMainMenu(mainMenuItemList);
            selection = userInput.next();

            status = checkSelectionValid(mainMenuItemList, selection);

            printInvalidOptionMessage(status);

            if(status == menuStatuses.VALID){
                int option = Integer.parseInt(selection);
                switch (option){
                    case 1:
                        listPublicationDetails(bookList);
                        break;
                    case 2:
                        processPublication(bookList, Publication.actions.CHECKOUT);
                        break;
                    case 3:
                        processPublication(bookList, Publication.actions.RETURN);
                        break;
                    case 4:
                        listPublicationDetails(movieList);
                        break;
                    case 5:
                        processPublication(movieList, Publication.actions.CHECKOUT);
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
}
