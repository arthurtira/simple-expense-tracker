package com.arthurtira.tracker.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExpenseResponseData {
    private List<ExpenseDto> items;
    private ExpensesSummaryDto summary;
}
