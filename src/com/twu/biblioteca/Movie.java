package com.twu.biblioteca;

public class Movie extends Publication {
    public static final int R_COL = 10;
    private String rating;

    public Movie (String title, String director, String year, String rating) {
        super(title, director, year);
        setRating(rating);
    }

    public String setRating(String rating) {
        int iRating;
        try{
            iRating = Integer.parseInt(rating);
            if(iRating < 1)
                this.rating = "1";
            else if (iRating > 10)
                this.rating = "10";
            else
                this.rating = rating;
        } catch (NumberFormatException e) {
            if(rating.equals("unrated"))
                this.rating = rating;
        } finally {
            if (this.rating == null)
                this.rating = "unrated";
        }

        return this.rating;
    }

    public String toString() {
        return String.format("%-" + T_COL + "s | %-" + C_COL + "s | %-" +
                Y_COL + "s | %-" + R_COL + "s\n", title, creator, year, rating);
    }

    public String getDirector() {
        return creator;
    }

    public String getRating() {
        return rating;
    }
}
