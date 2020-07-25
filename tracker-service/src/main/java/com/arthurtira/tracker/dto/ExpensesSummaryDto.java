package com.arthurtira.tracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
@Builder
public class ExpensesSummaryDto {
    private BigDecimal totalSpent;
    private BigDecimal dailyAverage;
    private Map<String, BigDecimal> spendingByCategory;
    private String currency;
    private String category;
    private String startDate;
    private String endDate;
}
