package com.arthurtira.tracker.wrappers;

import com.arthurtira.tracker.dto.ExpenseResponseData;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpensesResponseWrapper<T> {
    private int size;
    private int page;
    private long totalItems;
    private int totalPages;
    private ExpenseResponseData data;
}
