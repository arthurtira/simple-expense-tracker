package com.arthurtira.tracker.services;

import com.arthurtira.tracker.dto.ExpenseDto;
import com.arthurtira.tracker.dto.ExpenseType;
import com.arthurtira.tracker.dto.ExpensesFilterRequest;
import com.arthurtira.tracker.exceptions.BadRequestException;
import com.arthurtira.tracker.model.Expense;
import com.arthurtira.tracker.model.UserEntity;
import com.arthurtira.tracker.wrappers.ExpensesResponseWrapper;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ExpensesService {
    ExpensesResponseWrapper getExpenses(UserEntity userEntity, ExpensesFilterRequest filterRequest, Pageable pageable);
    Optional<ExpenseDto> getExpenseByEntityId(String expenseId);
    Optional<Expense> getExpenseById(String expenseId);
    ExpenseDto createExpense(ExpenseDto expenseDto, UserEntity userEntity) throws BadRequestException;
    ExpenseDto updateExpense(Expense existingExpense, ExpenseDto expenseDto);
    void deleteExpense(String expenseId);
    List<ExpenseType> getExpenseCategories();
}
