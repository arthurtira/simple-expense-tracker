package com.arthurtira.tracker.services.impl;

import com.arthurtira.tracker.dto.ExpenseDto;
import com.arthurtira.tracker.dto.ExpenseType;
import com.arthurtira.tracker.dto.ExpensesFilterRequest;
import com.arthurtira.tracker.enums.ExpenseCategory;
import com.arthurtira.tracker.mappers.ExpensesMapper;
import com.arthurtira.tracker.model.Expense;
import com.arthurtira.tracker.model.UserEntity;
import com.arthurtira.tracker.repository.ExpensesRepository;
import com.arthurtira.tracker.services.ExpensesService;
import com.arthurtira.tracker.dto.ExpensesSummaryDto;
import com.arthurtira.tracker.util.DateUtil;
import com.arthurtira.tracker.wrappers.ExpensesResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Slf4j
public class ExpensesServiceImpl implements ExpensesService {

    @Value("${tracker.currency:USD}")
    private String currency;
    ExpensesMapper mapper;
    ExpensesRepository repository;

    public ExpensesServiceImpl(ExpensesMapper mapper, ExpensesRepository expensesRepository) {
        this.mapper = mapper;
        this.repository = expensesRepository;
    }

    @Override
    public ExpensesResponseWrapper getExpenses(UserEntity userEntity, ExpensesFilterRequest filterRequest, Pageable pageable) {
        Page<Expense> expenses;

        log.debug("Filter request {} "  + filterRequest);

        if(filterRequest.getFromDate() == null)
            filterRequest.setFromDate(DateUtil.sevenDaysAgo());
        if(filterRequest.getToDate() == null)
            filterRequest.setToDate(new Date());

        filterRequest.setToDate(DateUtil.endOfDay(filterRequest.getToDate()));

        if(filterRequest.getCategory().equalsIgnoreCase("All")) {
            expenses = repository.findAllByUserEntityAndCreatedOnAfterAndCreatedOnBefore(userEntity, filterRequest.getFromDate(),
                    filterRequest.getToDate(), pageable);
        }else {
            expenses =  repository.findAllByUserEntityAndCategoryAndCreatedOnAfterAndCreatedOnBefore(userEntity,filterRequest.getCategory()
                    , filterRequest.getFromDate(), filterRequest.getToDate(), pageable);
        }

        return buildResponse(expenses, filterRequest);

    }

    private ExpensesResponseWrapper buildResponse(Page<Expense> expenses, ExpensesFilterRequest request) {
        if(expenses == null) {
            return ExpensesResponseWrapper.builder().build();
        }
        List<ExpenseDto> expenseDtos = this.mapper.toDtoStream(expenses.get())
                .peek(expenseDto -> expenseDto.setCurrency(currency))
                .collect(Collectors.toList());

        BigDecimal sum = BigDecimal.ZERO;
        Map<String, BigDecimal> totalByCategory = new HashMap<>();
        for( ExpenseDto expenseDto: expenseDtos) {
            sum = sum.add(expenseDto.getAmount());
            totalByCategory.put(expenseDto.getCategory(),
                    totalByCategory.getOrDefault(expenseDto.getCategory(), BigDecimal.ZERO).add(expenseDto.getAmount()));
        }

        long diffInMillies = Math.abs(request.getToDate().getTime() - request.getFromDate().getTime());
        long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        log.debug("Days {} " + days);

        ExpensesSummaryDto expensesSummary = ExpensesSummaryDto.builder()
                .totalSpent(sum)
                .dailyAverage(sum.divide(new BigDecimal(days), 2, BigDecimal.ROUND_UP))
                .endDate(DateUtil.formatDateForString(request.getToDate()))
                .startDate(DateUtil.formatDateForString(request.getFromDate()))
                .currency(currency)
                .category(request.getCategory())
                .spendingByCategory(totalByCategory)
                .build();

        log.debug("Summary response {} " + expensesSummary);

        return ExpensesResponseWrapper.builder()
                .page(expenses.getNumber())
                .size(expenses.getNumberOfElements())
                .totalItems(expenses.getTotalElements())
                .totalPages(expenses.getTotalPages())
                .data(expenseDtos)
                .summary(expensesSummary)
                .build();
    }


    @Override
    public Optional<ExpenseDto> getExpenseByEntityId(String expenseId) {
        Optional<Expense> optionalExpense = this.repository.findByEntityId(expenseId);
        if(optionalExpense.isPresent()) {
            return Optional.of(this.mapper.toDto(optionalExpense.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Expense> getExpenseById(String expenseId) {
       return this.repository.findByEntityId(expenseId);
    }


    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto, UserEntity userEntity) {
        Expense expense = this.mapper.fromDto(expenseDto);
        log.debug(" Expense {} " + expense);
        expense.setUserEntity(userEntity);
        return mapper.toDto(this.repository.save(expense));
    }

    @Override
    public ExpenseDto updateExpense(Expense existingExpense, ExpenseDto expenseDto) {
        existingExpense.setAmount(expenseDto.getAmount());
        existingExpense.setCategory(expenseDto.getCategory());
        existingExpense.setComment(expenseDto.getComment());
        existingExpense.setDescription(expenseDto.getDescription());

        Expense updated = this.repository.save(existingExpense);
        return mapper.toDto(updated);
    }

    @Override
    public void deleteExpense(String expenseId) {
        Optional<Expense> expenseOptional = this.repository.findByEntityId(expenseId);
        if(expenseOptional.isPresent()) {
            repository.delete(expenseOptional.get());
        }else {
            throw new RuntimeException("Expense not found");
        }
    }

    @Override
    public List<ExpenseType> getExpenseCategories() {
        List<ExpenseType> groups = new ArrayList<>();

        Stream.of(ExpenseCategory.values())
                .forEach(group -> groups.add(ExpenseType.builder().type(group.name()).description(group.getDescription()).build()));

        return groups;

    }
}
