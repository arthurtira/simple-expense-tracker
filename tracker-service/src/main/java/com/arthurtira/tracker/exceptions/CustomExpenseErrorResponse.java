package com.arthurtira.tracker.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CustomExpenseErrorResponse {
    private Date timestamp;
    private String message;
    private String errorMessage;
}
