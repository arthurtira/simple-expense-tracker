package com.arthurtira.tracker.wrappers;

import com.arthurtira.tracker.dto.ExpenseDto;
import com.arthurtira.tracker.dto.ExpensesSummaryDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpensesResponseWrapper<T> {
    private int size;
    private int page;
    private long totalItems;
    private int totalPages;
    private List<ExpenseDto> data;
    private ExpensesSummaryDto summary;
}
