package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ExampleTest {
    private BibliotecaApp app;
    private PrintStream originalOutStream;
    private ByteArrayOutputStream testOutStream;

    @Before
    public void setUp() {
        app = new BibliotecaApp();

        originalOutStream = System.out;
        testOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutStream));
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

        String expectedMessage = "Hello World\nMy First TDD Program\n";

        app.listBooks(books);
        String outputMessage =  testOutStream.toString();

        assertEquals(expectedMessage, outputMessage);
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
    public void testShowMainMenu() {
        String expected = "Main Menu:\n1. List Books";
        assertEquals(expected, testOutStream.toString());
    }
}
