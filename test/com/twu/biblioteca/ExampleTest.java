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
            expected.append(book.toString());
        }

        BibliotecaApp.listBookDetails(bookList);
        assertEquals(expected.toString(), testOutStream.toString());
    }

    @Test
    public void testBookClassCheckoutBook() {
        Book book = bookList.get(0);
        assertEquals(true, book.checkoutBook());
        assertEquals(false, book.checkoutBook());
    }

    @Test
    public void testBookClassReturnBook() {
        Book book = bookList.get(0);
        assertEquals(false, book.returnBook());
        book.checkoutBook();
        assertEquals(true, book.returnBook());
    }

    @Test
    public void testCheckoutBookByBook() {
        Book book = bookList.get(0);
        assertEquals(true, BibliotecaApp.checkoutBookByBook(book));
        assertEquals("Thank you! Enjoy the book\n", testOutStream.toString());
        testOutStream.reset();
        assertEquals(false, BibliotecaApp.checkoutBookByBook(book));
        assertEquals("That book is not available.\n", testOutStream.toString());
    }

    @Test
    public void testReturnBookByBook() { //to be updated/changed.
        Book book = bookList.get(0);
        BibliotecaApp.returnBookByBook(book);
        assertEquals("That is not a valid book to return.\n", testOutStream.toString());
        testOutStream.reset();
        book.checkoutBook();
        BibliotecaApp.returnBookByBook(book);
        assertEquals("Thank you for returning the book.\n", testOutStream.toString());
    }

    @Test
    public void testShowMenuOptions() {
        StringBuilder expected = new StringBuilder();
        int optNum = 1;

        for(String option: menuOptions) {
            expected.append(String.format("%d. %s\n", optNum, option));
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
        expected.append("-----\n");
        expected.append("Select option, and press Enter: ");

        BibliotecaApp.showMainMenu(menuOptions);
        assertEquals(expected.toString(), testOutStream.toString());
    }

    @Test
    public void testCheckSelectionValid() {
        setInputString("1");
        assertEquals(true, BibliotecaApp.checkSelectionValid(menuOptions));

        setInputString("H");
        assertEquals(false, BibliotecaApp.checkSelectionValid(menuOptions));

        setInputString("0");
        assertEquals(false, BibliotecaApp.checkSelectionValid(menuOptions));

        setInputString("100");
        assertEquals(false, BibliotecaApp.checkSelectionValid(menuOptions));
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
    public void testCheckoutBook(){
        //setup
        String title0 = bookList.get(0).getTitle();
        String title1 = bookList.get(1).getTitle();
        String notInList = "La la la";

        //tests
        setInputString(title0);
        assertEquals(true, BibliotecaApp.checkoutBook(bookList));

        setInputString(title0);
        assertEquals(false, BibliotecaApp.checkoutBook(bookList));

        setInputString(title1);
        assertEquals(true, BibliotecaApp.checkoutBook(bookList));

        setInputString(notInList);
        assertEquals(false, BibliotecaApp.checkoutBook(bookList));
    }

    @Test
    public void testReturnBook(){
        //setup
        String title0 = bookList.get(0).getTitle();
        String title1 = bookList.get(1).getTitle();
        String notInList = "La la la";

        assertTrue(BibliotecaApp.checkoutBookByBook(bookList.get(0)));

        //tests
        setInputString(title0);
        assertEquals(true, BibliotecaApp.returnBook(bookList));

        setInputString(title0);
        assertEquals(false, BibliotecaApp.returnBook(bookList));

        setInputString(title1);
        assertEquals(false, BibliotecaApp.returnBook(bookList));

        setInputString(notInList);
        assertEquals(false, BibliotecaApp.returnBook(bookList));
    }

}
