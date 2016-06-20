package com.twu.biblioteca;

import java.util.ArrayList;

public class Library {
    enum publicationType {BOOK, MOVIE}
    private ArrayList<Publication> availableBooks;
    private ArrayList<Publication> availableMovies;

    public Library() {
        availableBooks = new ArrayList<Publication>();
        availableBooks.add(new Book("TW101", "ThoughtWorkers", "2012"));
        availableBooks.add(new Book("Just in time debug", "Various", "2011"));
        availableBooks.add(new Book("Let me Fly", "Testers Union", "2016"));
        availableBooks.add(new Book("TDD is interesting", "John et. al", "2014"));

        availableMovies = new ArrayList<Publication>();
        availableMovies.add(new Movie("ThoughtWorks History", "Infant Thomas", "2016", "10"));
        availableMovies.add(new Movie("ThoughtWorks Staffing", "Anshul", "2016", ""));
        availableMovies.add(new Movie("ThoughtWorks Fun & Games", "TWers", "2016", "unrated"));
    }

    private ArrayList<Publication> getPublicationList(publicationType type) {
        switch(type) {
            case BOOK:
                return availableBooks;
            case MOVIE:
                return availableMovies;
        }
        return null;
    }

    public void listPublicationDetails(publicationType type) {
        ArrayList<Publication> pubList = getPublicationList(type);

        for(Publication pub: pubList) {
            if (!pub.getOnLoan()) System.out.printf(pub.toString());
        }
    }

    private int findPublicationByTitle(String title, publicationType type){
        int idx = -1;
        ArrayList<Publication> pubList = getPublicationList(type);

        for(Publication pub : pubList){
            idx++;
            if(title.equals(pub.getTitle())) {
                return idx;
            }
        }

        return -1;
    }

    public boolean processPublication(String input, Publication.actions action, publicationType type, String userid) {
        ArrayList<Publication> pubList = getPublicationList(type);

        int index = findPublicationByTitle(input, type);

        if(index == -1) {
            pubList.get(0).printNotification(action, false);
            return false;
        }

        Publication pub = pubList.get(index);
        boolean status = pub.updateStatus(action);

        if(status && action == Publication.actions.CHECKOUT)
            pub.setBorrower(userid);
        else if (status && action == Publication.actions.RETURN)
            pub.setBorrower(null);

        pub.printNotification(action, status);

        return status;
    }
}
