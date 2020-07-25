package com.arthurtira.tracker.enums;

public enum ExpenseCategory {
    HOUSEHOLD("All household expenses"), ENTERTAINMENT ("Entertainment is cool");

    private String description;

    ExpenseCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
