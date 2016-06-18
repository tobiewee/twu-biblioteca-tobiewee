package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ExampleTest {
    private PrintStream originalOutStream;
    private InputStream originalInStream;
    private ByteArrayOutputStream testOutStream;
    private ArrayList<String> menuOptions;
    private ArrayList<Publication> bookList;
    private String defaultUsrPw;
    private String defaultUsrId;
    private ArrayList<User> userAccounts;


    @Before
    public void setUp() {
        originalOutStream = System.out;
        originalInStream = System.in;

        testOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutStream));

        bookList = new ArrayList<Publication>();
        bookList.add(new Book("Jump in TDD", "Unknown", "2017"));
        bookList.add(new Book("Mouse loves cat", "Rubbish Author", "2020"));

        menuOptions = new ArrayList<String>();
        menuOptions.add("List books");

        defaultUsrId = "xxx-xxxx";
        defaultUsrPw = "test123!";

        userAccounts = new ArrayList<User>();
        userAccounts.add(new User(defaultUsrId, "John Dow", "Johnny@LOL.com", "+0912398743"));
        userAccounts.add(new User("xx1-xxxy", "JK Laughing", "haha@Best.com", "+743725312"));
    }

    @After
    public void tearDown() {
        System.setOut(originalOutStream);
        System.setIn(originalInStream);
    }

    private void setInputString(String strToSet) {
        ByteArrayInputStream testInStream = new ByteArrayInputStream(strToSet.getBytes());
        System.setIn(testInStream);
    }

    @Test
    public void testDisplayWelcomeMessage() {
        String expectedMessage = "Welcome to Biblioteca!\n";
        BibliotecaApp.displayWelcomeMessage();
        String outputMessage =  testOutStream.toString();
        assertEquals(expectedMessage, outputMessage);
    }

    @Test
    public void testListBooks() {
        ArrayList<String> books = new ArrayList<String>();
        for (Publication pub : bookList) {
            books.add(pub.getTitle());
        }

        StringBuilder expectedMessage = new StringBuilder();
        for(String title: books) {
            expectedMessage.append(title);
            expectedMessage.append("\n");
        }

        BibliotecaApp.listBooks(books);
        String outputMessage =  testOutStream.toString();

        assertEquals(expectedMessage.toString(), outputMessage);
    }

    @Test
    public void testPrintPublicationDetails() {
        Book book = (Book) bookList.get(0);
        String expected = book.toString();

        Movie movie = new Movie("Test", "Director", "2015", "unrated");

        BibliotecaApp.printPublicationDetails(book);
        assertEquals(expected, testOutStream.toString());

        testOutStream.reset();

        BibliotecaApp.printPublicationDetails(movie);
        assertEquals(movie.toString(), testOutStream.toString());
    }

    @Test
    public void testListPublicationDetails() {
        StringBuilder expected = new StringBuilder();
        for(Publication pub: bookList) {
            if (!pub.getOnLoan()) expected.append(pub.toString());
        }

        BibliotecaApp.listPublicationDetails(bookList);
        assertEquals(expected.toString(), testOutStream.toString());

        testOutStream.reset();
        assertTrue(bookList.get(0).updateStatus(Publication.actions.CHECKOUT));
        expected = new StringBuilder();
        for(Publication pub: bookList) {
            if (!pub.getOnLoan()) expected.append(pub.toString());
        }

        BibliotecaApp.listPublicationDetails(bookList);
        assertEquals(expected.toString(), testOutStream.toString());
    }

    @Test
    public void testShowMenuOptions() {
        StringBuilder expected = new StringBuilder();
        int optNum = 1;

        for(String option: menuOptions) {
            expected.append(String.format("%d. %s\n", optNum, option));
            optNum++;
        }

        BibliotecaApp.showMenuOptions(menuOptions);
        assertEquals(expected.toString(), testOutStream.toString());
    }

    @Test
    public void testShowMainMenu() {
        StringBuilder expected = new StringBuilder();
        int optNum = 1;
        expected.append("Main Menu:\n");
        for(String option: menuOptions) {
            expected.append(String.format("%d. %s\n", optNum, option));
        }
        expected.append("Type 'Quit' to exit.\n");
        expected.append("-----\n");
        expected.append("Select option, and press Enter: ");

        BibliotecaApp.showMainMenu(menuOptions);
        assertEquals(expected.toString(), testOutStream.toString());
    }

    @Test
    public void testCheckSelectionValid() {
        assertEquals(BibliotecaApp.menuStatuses.VALID,
                BibliotecaApp.checkSelectionValid(menuOptions, "1"));

        assertEquals(BibliotecaApp.menuStatuses.INVALID,
                BibliotecaApp.checkSelectionValid(menuOptions, "H"));

        assertEquals(BibliotecaApp.menuStatuses.INVALID,
                BibliotecaApp.checkSelectionValid(menuOptions, "0"));

        assertEquals(BibliotecaApp.menuStatuses.INVALID,
                BibliotecaApp.checkSelectionValid(menuOptions, "100"));

        assertEquals(BibliotecaApp.menuStatuses.QUIT,
                BibliotecaApp.checkSelectionValid(menuOptions, "Quit"));
    }

    @Test
    public void testFindPublicationByTitle(){
        String title0 = bookList.get(0).getTitle();
        String title1 = bookList.get(1).getTitle();
        assertEquals(0, BibliotecaApp.findPublicationByTitle(title0, bookList));
        assertEquals(1, BibliotecaApp.findPublicationByTitle(title1, bookList));
    }

    @Test
    public void testAskForTitle() {
        BibliotecaApp.askForTitle();
        assertEquals("Enter title: ", testOutStream.toString());
    }

    @Test
    public void testProcessPublication(){
        //setup
        String title0 = bookList.get(0).getTitle();
        String title1 = bookList.get(1).getTitle();
        String notInList = "La la la";
        String usrID = userAccounts.get(0).getId();

        //tests
        setInputString(title0);
        assertEquals(true, BibliotecaApp.processPublication(bookList, Publication.actions.CHECKOUT, usrID));

        setInputString(title0);
        assertEquals(false, BibliotecaApp.processPublication(bookList, Publication.actions.CHECKOUT, usrID));

        setInputString(title0);
        assertEquals(true, BibliotecaApp.processPublication(bookList, Publication.actions.RETURN, usrID));

        setInputString(title0);
        assertEquals(false, BibliotecaApp.processPublication(bookList, Publication.actions.RETURN, usrID));

        setInputString(title1);
        assertEquals(false, BibliotecaApp.processPublication(bookList, Publication.actions.RETURN, usrID));

        setInputString(notInList);
        assertEquals(false, BibliotecaApp.processPublication(bookList, Publication.actions.CHECKOUT, usrID));

        setInputString(notInList);
        assertEquals(false, BibliotecaApp.processPublication(bookList, Publication.actions.RETURN, usrID));
    }

    @Test
    public void testBookClassPrintNotification() {
        //setup
        String chkoutBkSucc = "Thank you! Enjoy the book\n";
        String chkoutBkFail = "That book is not available.\n";
        String returnBkSucc = "Thank you for returning the book.\n";
        String returnBkFail = "That is not a valid book to return.\n";

        Book test = (Book) bookList.get(0);

        test.printNotification(Publication.actions.CHECKOUT, true);
        assertEquals(chkoutBkSucc, testOutStream.toString());

        testOutStream.reset();

        test.printNotification(Publication.actions.CHECKOUT, false);
        assertEquals(chkoutBkFail, testOutStream.toString());

        testOutStream.reset();

        test.printNotification(Publication.actions.RETURN, true);
        assertEquals(returnBkSucc, testOutStream.toString());

        testOutStream.reset();

        test.printNotification(Publication.actions.RETURN, false);
        assertEquals(returnBkFail, testOutStream.toString());
    }

    @Test
    public void testPrintInvalidOptionMessage() {
        BibliotecaApp.printInvalidOptionMessage(BibliotecaApp.menuStatuses.INVALID);
        assertEquals("Select a valid option!\n", testOutStream.toString());
        testOutStream.reset();
        BibliotecaApp.printInvalidOptionMessage(BibliotecaApp.menuStatuses.VALID);
        assertEquals("", testOutStream.toString());
        testOutStream.reset();
        BibliotecaApp.printInvalidOptionMessage(BibliotecaApp.menuStatuses.QUIT);
        assertEquals("", testOutStream.toString());
    }

    @Test
    public void testCreateNewBook() {
        String title = "I am such a Legend";
        String author = "Binny K.";
        String year = "2016";

        Book test = new Book(title, author, year);
        assertEquals(title, test.getTitle());
        assertEquals(author, test.getAuthor());
        assertEquals(year, test.getYear());
    }

    @Test
    public void testBookClassCheckoutBook() {
        Book book = (Book) bookList.get(0);
        assertEquals(true, book.updateStatus(Publication.actions.CHECKOUT));
        assertEquals(false, book.updateStatus(Publication.actions.CHECKOUT));
    }

    @Test
    public void testBookClassReturnBook() {
        Book book = (Book) bookList.get(0);
        assertEquals(false, book.updateStatus(Publication.actions.RETURN));
        assertTrue(book.updateStatus(Publication.actions.CHECKOUT));
        assertEquals(true, book.updateStatus(Publication.actions.RETURN));
    }

    @Test
    public void testPublicationClassUpdateStatus() {
        Book book1 = (Book) bookList.get(0);
        assertFalse(book1.getOnLoan());
        assertTrue(book1.updateStatus(Publication.actions.CHECKOUT));
        assertFalse(book1.updateStatus(Publication.actions.CHECKOUT));
        assertTrue(book1.updateStatus(Publication.actions.RETURN));
        assertFalse(book1.updateStatus(Publication.actions.RETURN));
    }

    @Test
    public void testCreateNewMovie() {
        String title = "I am such a Legend";
        String director = "Funnyguy123 K.";
        String year = "2016";
        String rating = "5";
        Movie test = new Movie(title, director, year, rating);
        assertEquals(title, test.getTitle());
        assertEquals(director, test.getDirector());
        assertEquals(year, test.getYear());
        assertEquals(rating, test.getRating());
    }

    @Test
    public void testMovieClassSetRating() {
        String rating = "5";
        String notRated = "unrated";
        String minRating = "1";
        String maxRating = "10";
        Movie test = new Movie("", "", "", "");
        assertEquals(notRated, test.getRating());

        assertEquals(rating, test.setRating(rating));
        assertEquals(minRating, test.setRating("0"));
        assertEquals(maxRating, test.setRating("11"));

        String currentRating = test.getRating();
        assertEquals(currentRating, test.setRating("asdf"));
        assertEquals(currentRating, test.setRating(""));

        assertEquals(notRated, test.setRating("unrated"));
    }

    @Test
    public void testCreateNewUser() {
        User test = userAccounts.get(0);
        assertTrue(test.isInit());
        assertEquals(defaultUsrId, test.getId());
        assertEquals(defaultUsrPw, test.getPw());
    }

    @Test
    public void testUserClassVerifyPassword() {
        User test = userAccounts.get(0);
        assertTrue(test.isInit());
        assertEquals(true, test.verifyPassword(defaultUsrPw));
        assertEquals(false, test.verifyPassword("asdjf"));
    }

    @Test
    public void testUserClassEquals() {
        User usr1 = userAccounts.get(0);
        assertTrue(usr1.isInit());

        User usr2 = userAccounts.get(1);
        assertTrue(usr2.isInit());

        User usr3 = new User(usr1.getId(), usr2.getName(), usr1.getEmail(), usr2.getPhoneNum());
        assertTrue(usr3.isInit());


        assertEquals(true, usr1.equals(usr1));
        assertEquals(false, usr1.equals(usr2));
        assertEquals(true, usr1.equals(usr3));
    }

    @Test
    public void testPromptLogin() {
        BibliotecaApp.promptLogin();
        assertEquals("Enter login id: ", testOutStream.toString());
    }

    @Test
    public void testPromptPassword() {
        BibliotecaApp.promptPassword();
        assertEquals("Enter password: ", testOutStream.toString());
    }

    @Test
    public void testVerifyLogin() {
        assertEquals(true, BibliotecaApp.verifyLogin(userAccounts, defaultUsrId, defaultUsrPw));
        assertEquals(false, BibliotecaApp.verifyLogin(userAccounts, "Lalala", defaultUsrPw));
        assertEquals(false, BibliotecaApp.verifyLogin(userAccounts, defaultUsrId, "fake"));
        assertEquals(false, BibliotecaApp.verifyLogin(userAccounts, "Gibberish", "Hmph"));
    }

    @Test
    public void testDisplayUserDetails() {
        User test = userAccounts.get(0);
        assertTrue(test.isInit());

        String expected = String.format("Name: %s\nEmail: %s\nPhone: %s\n",
                test.getName(), test.getEmail(), test.getPhoneNum());
        BibliotecaApp.displayUserDetails(test);
        assertEquals(expected, testOutStream.toString());
    }

    @Test
    public void testGetUser() {
        User expected = userAccounts.get(0);
        assertTrue(expected.isInit());

        assertEquals(expected, BibliotecaApp.getUser(userAccounts, expected.getId()));
        assertEquals(null, BibliotecaApp.getUser(userAccounts, "test"));
    }
}
