package com.arthurtira.tracker.exceptions;

public class ExpenseNotFoundException extends Exception {
    private static final long serialVersionUID = 1l;

    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
