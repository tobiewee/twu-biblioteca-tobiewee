package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

class Library {
    enum publicationType {BOOK, MOVIE}
    enum actions {CHECKOUT, RETURN}

    private ArrayList<Publication> availableBooks;
    private ArrayList<Publication> borrowedBooks;
    private ArrayList<Publication> availableMovies;
    private ArrayList<Publication> borrowedMovies;
    private Map<Publication, String> loanedBy;

    Library() {
        availableBooks = new ArrayList<Publication>();
        availableBooks.add(new Book("TW101", "ThoughtWorkers", "2012"));
        availableBooks.add(new Book("Just in time debug", "Various", "2011"));
        availableBooks.add(new Book("Let me Fly", "Testers Union", "2016"));
        availableBooks.add(new Book("TDD is interesting", "John et. al", "2014"));

        borrowedBooks = new ArrayList<Publication>();

        availableMovies = new ArrayList<Publication>();
        availableMovies.add(new Movie("ThoughtWorks History", "Infant Thomas", "2016", "10"));
        availableMovies.add(new Movie("ThoughtWorks Staffing", "Anshul", "2016", ""));
        availableMovies.add(new Movie("ThoughtWorks Fun & Games", "TWers", "2016", "unrated"));

        borrowedMovies = new ArrayList<Publication>();

        loanedBy = new Hashtable<Publication, String>();
    }

    ArrayList<Publication> getPublicationList(publicationType type, actions action) {
        switch(type) {
            case BOOK:
                if(action == actions.CHECKOUT) return availableBooks;
                else return borrowedBooks;
            case MOVIE:
                if(action == actions.CHECKOUT) return availableMovies;
                else return borrowedMovies;
        }
        return null;
    }

    private Publication findPublicationByTitle(String title, publicationType type, actions action){
        ArrayList<Publication> pubList = getPublicationList(type, action);

        for(Publication pub : pubList){
            if(title.equals(pub.getTitle())) {
                return pub;
            }
        }
        return null;
    }

    private void transferFromAToB(Publication pub, ArrayList<Publication> A, ArrayList<Publication> B){
        A.remove(pub);
        B.add(pub);
    }

    boolean processPublication(String title, actions action, publicationType type, String userid) {
        Publication pub = findPublicationByTitle(title, type, action);

        if (pub == null) {
            return false;
        }

        if (action == actions.CHECKOUT) {
            if (type == publicationType.BOOK)
                transferFromAToB(pub, availableBooks, borrowedBooks);
            else
                transferFromAToB(pub, availableMovies, borrowedMovies);
            loanedBy.put(pub, userid);
        } else if (action == actions.RETURN) {
            if (type == publicationType.BOOK)
                transferFromAToB(pub, borrowedBooks, availableBooks);
            else
                transferFromAToB(pub, borrowedMovies, availableMovies);
            loanedBy.remove(pub);
        }

        return true;
    }

    String getBorrower(Publication pub){
        return loanedBy.get(pub);
    }
}
