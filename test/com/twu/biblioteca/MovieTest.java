package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MovieTest {
    private Movie myMov;

    @Before
    public void setUp() {
        myMov = new Movie("Movie Title", "Director", "2016", "10");
    }

    @Test
    public void testGetDirector() {
        assertEquals("Director", myMov.getDirector());
    }

    @Test
    public void testGetRating() {
        assertEquals("10", myMov.getRating());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Movie Title", myMov.getTitle());
    }

    @Test
    public void testGetYear() {
        assertEquals("2016", myMov.getYear());
    }

    @Test
    public void testSetRating() {
        assertTrue(myMov.getRating().equals("10"));
        assertEquals("1", myMov.setRating("1"));
        assertEquals("unrated", myMov.setRating("unrated"));
        assertEquals("1", myMov.setRating("0"));
        assertEquals("10", myMov.setRating("11"));
        assertEquals("unrated", myMov.setRating(null));
        assertEquals("5", myMov.setRating("5"));
    }
}
