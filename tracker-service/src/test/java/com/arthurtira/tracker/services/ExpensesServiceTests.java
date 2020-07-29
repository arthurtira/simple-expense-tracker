package com.arthurtira.tracker.services;

import com.arthurtira.tracker.dto.ExpenseDto;
import com.arthurtira.tracker.dto.ExpensesFilterRequest;
import com.arthurtira.tracker.exceptions.BadRequestException;
import com.arthurtira.tracker.mappers.MapperConfig;
import com.arthurtira.tracker.model.Expense;
import com.arthurtira.tracker.model.UserEntity;
import com.arthurtira.tracker.repository.ExpensesRepository;
import com.arthurtira.tracker.services.impl.ExpensesServiceImpl;
import com.arthurtira.tracker.wrappers.ExpensesResponseWrapper;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExpensesServiceImpl.class, MapperConfig.class})
public class ExpensesServiceTests {

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
    public void createExpense_valid() throws BadRequestException {
        UserEntity userEntity = new UserEntity();
        ExpenseDto expenseDto = createTestExpenseDTO();
        when(userEntityService.findUserEntityByUsername("username")).thenReturn(userEntity);
        when(expensesRepository.save(any(Expense.class))).thenReturn(createTestExpense());
        assertNotNull(expensesService.createExpense(expenseDto, userEntity));
        verify(expensesRepository, times(1)).save(any(Expense.class));
    }

    @Test
    public void getExpenses() {
        Pageable pageable =
                PageRequest.of(Integer.valueOf(0), Integer.valueOf(20), Sort.by("amount").descending());

        ExpensesFilterRequest filterRequest = ExpensesFilterRequest.builder()
                .toDate(new Date())
                .fromDate(new Date())
                .category("All")
                .build();

        UserEntity userEntity = new UserEntity();

        when(expensesRepository.findAllByUserEntityAndCreatedOnAfterAndCreatedOnBefore(userEntity,new Date(), new Date(), pageable))
                .thenReturn(createPageExpense());
        when(expensesRepository.findAllByUserEntityAndCategoryAndCreatedOnAfterAndCreatedOnBefore(userEntity, "All",new Date(), new Date(), pageable))
                .thenReturn(createPageExpense());

        ExpensesResponseWrapper responseWrapper = expensesService.getExpenses(userEntity, filterRequest, pageable);

        //assertEquals(Collections.singletonList(createTestExpenseDTO()), responseWrapper.getData());
        verify(expensesRepository, atMost(1)).findAllByUserEntityAndCreatedOnAfterAndCreatedOnBefore(userEntity,new Date(), new Date(), pageable);
        verify(expensesRepository, atMost(1)).findAllByUserEntityAndCategoryAndCreatedOnAfterAndCreatedOnBefore(userEntity, "All",new Date(), new Date(), pageable);
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

    private Page<Expense> createPageExpense() {
        Expense expense = new Expense();
        expense.setDescription("description");
        expense.setComment("comment");
        expense.setCategory("HOUSEHOLD");
        expense.setAmount(BigDecimal.TEN);
        expense.setCreatedOn(new Date());
        return new PageImpl<>(Collections.singletonList(expense));
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


}
