package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ExampleTest {
    private PrintStream originalOutStream;
    private InputStream originalInStream;
    private ByteArrayOutputStream testOutStream;
    private ByteArrayInputStream testInStream;
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
        books.add("Hello World");
        books.add("My First TDD Program");

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
        Book book = new Book("Coding in TDD", "Jump Kitten", "2016");
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
    public void testCheckoutBookByBook() {
        Book book = new Book("Test", "Tester", "2016");
        assertEquals(true, BibliotecaApp.checkoutBookByBook(book));
        assertEquals("Thank you! Enjoy the book\n", testOutStream.toString());
        testOutStream.reset();
        assertEquals(false, BibliotecaApp.checkoutBookByBook(book));
        assertEquals("That book is not available.\n", testOutStream.toString());
    }

    @Test
    public void testReturnBookByBook() { //to be updated/changed.
        Book book = new Book("Test", "Tester", "2016");
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
        testInStream = new ByteArrayInputStream("1".getBytes());
        System.setIn(testInStream);
        assertEquals(true, BibliotecaApp.checkSelectionValid(menuOptions));

        testInStream = new ByteArrayInputStream("H".getBytes());
        System.setIn(testInStream);
        assertEquals(false, BibliotecaApp.checkSelectionValid(menuOptions));

        testInStream = new ByteArrayInputStream("0".getBytes());
        System.setIn(testInStream);
        assertEquals(false, BibliotecaApp.checkSelectionValid(menuOptions));

        testInStream = new ByteArrayInputStream("100".getBytes());
        System.setIn(testInStream);
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
    public void checkoutBook(){
        String title0 = bookList.get(0).getTitle();
        String title1 = bookList.get(1).getTitle();
        String notInList = "La la la";

        testInStream = new ByteArrayInputStream(title0.getBytes());

        System.setIn(testInStream);
        assertEquals(true, BibliotecaApp.checkoutBook(bookList));

        testInStream = new ByteArrayInputStream(title1.getBytes());
        System.setIn(testInStream);
        assertEquals(true, BibliotecaApp.checkoutBook(bookList));

        testInStream = new ByteArrayInputStream(title0.getBytes());
        System.setIn(testInStream);
        assertEquals(false, BibliotecaApp.checkoutBook(bookList));

        testInStream = new ByteArrayInputStream(notInList.getBytes());
        System.setIn(testInStream);
        assertEquals(false, BibliotecaApp.checkoutBook(bookList));
    }
}
