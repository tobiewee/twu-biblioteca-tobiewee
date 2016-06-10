package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.type.ArrayType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ExampleTest {
    private BibliotecaApp app;
    private PrintStream originalOutStream;
    private ByteArrayOutputStream testOutStream;
    private ArrayList<String> menuOptions;

    @Before
    public void setUp() {
        app = new BibliotecaApp();

        originalOutStream = System.out;
        testOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutStream));

        menuOptions = new ArrayList<String>();
        menuOptions.add("List books");
    }

    @After
    public void tearDown() {
        System.setOut(originalOutStream);
    }

    @Test
    public void testDisplayWelcomeMessage() {
        String expectedMessage = "Welcome to Biblioteca!\n";
        app.displayWelcomeMessage();
        String outputMessage =  testOutStream.toString();
        assertEquals(expectedMessage, outputMessage);
    }

    @Test
    public void testListBooks() {
        ArrayList<String> books = new ArrayList<String>();
        books.add("Hello World");
        books.add("My First TDD Program");

        StringBuilder expectedMessage = new StringBuilder();
        for(String title: books) {
            expectedMessage.append(title + "\n");
        }

        app.listBooks(books);
        String outputMessage =  testOutStream.toString();

        assertEquals(expectedMessage.toString(), outputMessage);
    }

    @Test
    public void testPrintBookDetails() {
        Book book = new Book("Coding in TDD", "Jump Kitten", "2016");
        String expected = book.toString();

        app.printBookDetails(book);
        assertEquals(expected, testOutStream.toString());
    }

    @Test
    public void testListBookDetails() {
        ArrayList<Book> bookList = new ArrayList<Book>();
        bookList.add(new Book("Jump in TDD", "Unknown", "2017"));
        bookList.add(new Book("Mouse loves cat", "Rubbish Author", "2020"));

        StringBuilder expected = new StringBuilder();
        for(Book book: bookList) {
            expected.append(book.toString());
        }

        app.listBookDetails(bookList);
        assertEquals(expected.toString(), testOutStream.toString());
    }

    @Test
    public void testBookClassCheckoutBook() {
        Book book = new Book("Test", "Tester", "2016");
        assertEquals(true, book.checkoutBook());
        assertEquals(false, book.checkoutBook());
    }

    @Test
    public void testBookClassReturnBook() {
        Book book = new Book("Test", "Tester", "2016");
        assertEquals(false, book.returnBook());
        book.checkoutBook();
        assertEquals(true, book.returnBook());
    }

    @Test
    public void testCheckoutBook() {
        Book book = new Book("Test", "Tester", "2016");
        app.checkoutBook(book);
        assertEquals("Thank you! Enjoy the book\n", testOutStream.toString());
        testOutStream.reset();
        app.checkoutBook(book);
        assertEquals("That book is not available.\n", testOutStream.toString());
    }

    @Test
    public void testReturnBook() {
        Book book = new Book("Test", "Tester", "2016");
        app.returnBook(book);
        assertEquals("That is not a valid book to return.\n", testOutStream.toString());
        testOutStream.reset();
        book.checkoutBook();
        app.returnBook(book);
        assertEquals("Thank you for returning the book.\n", testOutStream.toString());
    }

    @Test
    public void testShowMenuOptions() {
        StringBuilder expected = new StringBuilder();
        int optNum = 1;

        for(String option: menuOptions) {
            expected.append(String.format("%d. %s\n", optNum, option));
        }

        app.showMenuOptions(menuOptions);
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

        app.showMainMenu(menuOptions);
        assertEquals(expected.toString(), testOutStream.toString());
    }
}
