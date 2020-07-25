package com.arthurtira.tracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseType {
    private String type;
    private String description;
}
