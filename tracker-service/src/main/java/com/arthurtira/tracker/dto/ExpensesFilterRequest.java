package com.arthurtira.tracker.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class ExpensesFilterRequest {
    private String category;
    private Date fromDate;
    private Date toDate;

}
