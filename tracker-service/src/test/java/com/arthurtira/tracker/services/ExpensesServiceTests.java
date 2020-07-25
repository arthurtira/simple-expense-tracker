package com.arthurtira.tracker.services;

import com.arthurtira.tracker.dto.ExpenseDto;
import com.arthurtira.tracker.mappers.MapperConfig;
import com.arthurtira.tracker.model.Expense;
import com.arthurtira.tracker.model.UserEntity;
import com.arthurtira.tracker.repository.ExpensesRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExpensesService.class, MapperConfig.class})
public class ExpensesServiceTests {

    @Test
    public void test(){}
    /*
    @MockBean
    private ExpensesRepository expensesRepository;

    @MockBean
    private UserEntityService userEntityService;

    @Autowired
    private ExpensesService expensesService;

    @AfterEach
    public void afterEach() {
        verifyNoMoreInteractions(expensesRepository);
        verifyNoMoreInteractions(userEntityService);
        verifyNoMoreInteractions(expensesService);
    }

    @Test
    public void createExpense_valid() {
        UserEntity userEntity = new UserEntity();

        ExpenseDto expenseDto = createTestExpenseDTO();

        when(userEntityService.findUserEntityByUsername("username")).thenReturn(userEntity);
        when(expensesRepository.save(any(Expense.class))).thenReturn(createTestExpense());

        assertNotNull(expensesService.createExpense(expenseDto, userEntity));

        verify(expensesRepository, times(1)).save(any(Expense.class));
    }

    private Expense createTestExpense() {
        Expense expense = new Expense();
        expense.setDescription("description");
        expense.setComment("comment");
        expense.setCategory("HOUSEHOLD");
        expense.setAmount(BigDecimal.TEN);
        expense.setCreatedOn(new Date());

        return expense;
    }

    private ExpenseDto createTestExpenseDTO() {
        return ExpenseDto.builder()
                .expenseDate(new Date())
                .currency("USD")
                .description("description")
                .comment("comment")
                .amount(BigDecimal.TEN)
                .category("HOUSEHOLD")
                .build();
    }


     */

}
