package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        assertEquals(0, app.bookList.size());
    }
}
