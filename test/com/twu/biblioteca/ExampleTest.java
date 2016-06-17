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
    private ArrayList<Book> bookList;

    @Before
    public void setUp() {
        originalOutStream = System.out;
        originalInStream = System.in;

        testOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutStream));

        bookList = new ArrayList<Book>();
        bookList.add(new Book("Jump in TDD", "Unknown", "2017"));
        bookList.add(new Book("Mouse loves cat", "Rubbish Author", "2020"));

        menuOptions = new ArrayList<String>();
        menuOptions.add("List books");
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
        for (Book book : bookList) {
            books.add(book.getTitle());
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
    public void testPrintBookDetails() {
        Book book = bookList.get(0);
        String expected = book.toString();

        BibliotecaApp.printBookDetails(book);
        assertEquals(expected, testOutStream.toString());
    }

    @Test
    public void testListBookDetails() {
        StringBuilder expected = new StringBuilder();
        for(Book book: bookList) {
            if (!book.getOnLoan()) expected.append(book.toString());
        }

        BibliotecaApp.listBookDetails(bookList);
        assertEquals(expected.toString(), testOutStream.toString());

        testOutStream.reset();
        assertTrue(bookList.get(0).updateStatus(Publication.actions.CHECKOUT));
        expected = new StringBuilder();
        for(Book book: bookList) {
            if (!book.getOnLoan()) expected.append(book.toString());
        }

        BibliotecaApp.listBookDetails(bookList);
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
    public void testFindBookByTitle(){
        String title0 = bookList.get(0).getTitle();
        String title1 = bookList.get(1).getTitle();
        assertEquals(0, BibliotecaApp.findBookByTitle(title0, bookList));
        assertEquals(1, BibliotecaApp.findBookByTitle(title1, bookList));
    }

    @Test
    public void testAskForBookTitle(){
        BibliotecaApp.askForBookTitle();
        assertEquals("Enter book title: ", testOutStream.toString());
    }

    @Test
    public void testProcessBook(){
        //setup
        String title0 = bookList.get(0).getTitle();
        String title1 = bookList.get(1).getTitle();
        String notInList = "La la la";

        //tests
        setInputString(title0);
        assertEquals(true, BibliotecaApp.processBook(bookList, Publication.actions.CHECKOUT));

        setInputString(title0);
        assertEquals(false, BibliotecaApp.processBook(bookList, Publication.actions.CHECKOUT));

        setInputString(title0);
        assertEquals(true, BibliotecaApp.processBook(bookList, Publication.actions.RETURN));

        setInputString(title0);
        assertEquals(false, BibliotecaApp.processBook(bookList, Publication.actions.RETURN));

        setInputString(title1);
        assertEquals(false, BibliotecaApp.processBook(bookList, Publication.actions.RETURN));

        setInputString(notInList);
        assertEquals(false, BibliotecaApp.processBook(bookList, Publication.actions.CHECKOUT));

        setInputString(notInList);
        assertEquals(false, BibliotecaApp.processBook(bookList, Publication.actions.RETURN));
    }

    @Test
    public void testPrintNotification() {
        //setup
        String chkoutBkSucc = "Thank you! Enjoy the book\n";
        String chkoutBkFail = "That book is not available.\n";
        String returnBkSucc = "Thank you for returning the book.\n";
        String returnBkFail = "That is not a valid book to return.\n";

        BibliotecaApp.printNotification(Publication.actions.CHECKOUT, true);
        assertEquals(chkoutBkSucc, testOutStream.toString());

        testOutStream.reset();

        BibliotecaApp.printNotification(Publication.actions.CHECKOUT, false);
        assertEquals(chkoutBkFail, testOutStream.toString());

        testOutStream.reset();

        BibliotecaApp.printNotification(Publication.actions.RETURN, true);
        assertEquals(returnBkSucc, testOutStream.toString());

        testOutStream.reset();

        BibliotecaApp.printNotification(Publication.actions.RETURN, false);
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
    public void testBookClassCheckoutBook() {
        Book book = bookList.get(0);
        assertEquals(true, book.updateStatus(Publication.actions.CHECKOUT));
        assertEquals(false, book.updateStatus(Publication.actions.CHECKOUT));
    }

    @Test
    public void testBookClassReturnBook() {
        Book book = bookList.get(0);
        assertEquals(false, book.updateStatus(Publication.actions.RETURN));
        assertTrue(book.updateStatus(Publication.actions.CHECKOUT));
        assertEquals(true, book.updateStatus(Publication.actions.RETURN));
    }

    @Test
    public void testPublicationClassUpdateStatus() {
        Book book1 = bookList.get(0);
        assertFalse(book1.getOnLoan());
        assertTrue(book1.updateStatus(Publication.actions.CHECKOUT));
        assertFalse(book1.updateStatus(Publication.actions.CHECKOUT));
        assertTrue(book1.updateStatus(Publication.actions.RETURN));
        assertFalse(book1.updateStatus(Publication.actions.RETURN));
    }
}
