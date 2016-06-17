package com.twu.biblioteca;

public abstract class Publication {
    public enum actions {CHECKOUT, RETURN}

    protected String title;
    protected String creator;
    protected String year;
    protected boolean onLoan;

    public Publication(String title, String creator, String year) {
        this.title = title;
        this.creator = creator;
        this.year = year;
        onLoan = false;
    }

    public abstract String toString();

    public boolean updateStatus(actions action) {
        switch (action) {
            case CHECKOUT:
                if(onLoan)
                    return false;
                break;
            case RETURN:
                if(!onLoan)
                    return false;
                break;
        }
        onLoan = !onLoan;
        return true;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public boolean getOnLoan() {
        return onLoan;
    }
}
