package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LibraryTest {
    private Library myLib;

    @Before
    public void setUp() {
        myLib = new Library();
    }

    @Test
    public void testProcessPublication() {
        assertEquals(false, myLib.processPublication("TW101", Library.actions.RETURN, Library.publicationType.BOOK, "123-1234"));
        assertEquals(true, myLib.processPublication("TW101", Library.actions.CHECKOUT, Library.publicationType.BOOK, "123-1234"));
        assertEquals(false, myLib.processPublication("TW101", Library.actions.CHECKOUT, Library.publicationType.BOOK, "123-1234"));
        assertEquals(true, myLib.processPublication("TW101", Library.actions.RETURN, Library.publicationType.BOOK, "123-1234"));
        assertEquals(false, myLib.processPublication("My Love", Library.actions.CHECKOUT, Library.publicationType.BOOK, "123-1234"));
    }

    @Test
    public void testGetBorrower() {
        ArrayList<Publication> availBooks = myLib.getPublicationList(Library.publicationType.BOOK, Library.actions.CHECKOUT);
        Publication pub = availBooks.get(0);
        assertTrue(myLib.processPublication(pub.getTitle(), Library.actions.CHECKOUT, Library.publicationType.BOOK, "123-1234"));
        assertEquals("123-1234", myLib.getBorrower(pub));
        assertTrue(myLib.processPublication(pub.getTitle(), Library.actions.RETURN, Library.publicationType.BOOK, "123-1234"));
    }
}
