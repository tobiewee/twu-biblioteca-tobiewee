package com.twu.biblioteca;

public abstract class Publication {
    public static final int T_COL = 45;
    public static final int C_COL = 45;
    public static final int Y_COL = 4;

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

    public void printNotification(actions action, boolean success){
        switch (action) {
            case CHECKOUT:
                if(success) System.out.print("Thank you!\n");
                else System.out.print("That is not available.\n");
                break;
            case RETURN:
                if(success) System.out.print("Thank you for returning.\n");
                else System.out.print("That is not a valid item to return.\n");
                break;
            default:
                break;
        }
    }

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
