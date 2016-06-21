package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookTest {
    private Book myPub;

    @Before
    public void setUp() {
        myPub = new Book("Title", "Creator", "2016");
    }

    @Test
    public void testGetTitle() {
        assertEquals("Title", myPub.getTitle());
    }
    @Test
    public void testGetAuthor() {
        assertEquals("Creator", myPub.getAuthor());
    }
    @Test
    public void testGetYear() {
        assertEquals("2016", myPub.getYear());
    }
}
