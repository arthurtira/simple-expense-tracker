package com.arthurtira.tracker.controllers;

import com.arthurtira.tracker.dto.ExpenseDto;
import com.arthurtira.tracker.dto.ExpenseType;
import com.arthurtira.tracker.dto.ExpensesFilterRequest;
import com.arthurtira.tracker.exceptions.ExpenseNotFoundException;
import com.arthurtira.tracker.model.UserEntity;
import com.arthurtira.tracker.repository.ExpensesRepository;
import com.arthurtira.tracker.services.ExpensesService;
import com.arthurtira.tracker.services.UserEntityService;
import com.arthurtira.tracker.dto.ExpenseResponseData;
import com.arthurtira.tracker.wrappers.ExpensesResponseWrapper;
import org.junit.After;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class ExpensesControllerTests {

    @Mock
    private ExpensesService expensesService;
    @Mock
    private ExpensesRepository expensesRepository;
    @Mock
    private UserEntityService userEntityService;
    @Mock
    private Principal principal;

    private ExpensesController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ExpensesController(expensesService, userEntityService);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(expensesService, expensesRepository, userEntityService);
    }

    private ExpenseDto buildExpenseDto() {
        return ExpenseDto.builder()
                .build();
    }

    private UserEntity buildUserEntity() {
        return new UserEntity();
    }

    private ExpenseType buildExpenseType() {
        return ExpenseType.builder().build();
    }

    private ExpensesFilterRequest buildFilterRequest() {
        return ExpensesFilterRequest.builder()
                .toDate(null).build();
    }

    @Test
    public void createExpense_RequestIsValid_createRequestAccepted() {
        ExpenseDto expenseDto = buildExpenseDto();
        UserEntity userEntity = buildUserEntity();

        when(userEntityService.findUserByPrincipal(principal)).thenReturn(Optional.of(userEntity));
        when(expensesService.createExpense(expenseDto, userEntity)).thenReturn(expenseDto);

        ResponseEntity<ExpenseDto> result = controller.createExpense(expenseDto, principal);

        Mockito.verify(userEntityService, times(1)).findUserByPrincipal(principal);
        Mockito.verify(expensesService, times(1)).createExpense(expenseDto, userEntity);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(result.getBody(), expenseDto);
    }


    @Test
    public void createExpense_RequestIsValid_badRequest() {
        ExpenseDto paymentDTO = buildExpenseDto();

        when(userEntityService.findUserByPrincipal(principal)).thenReturn(null);

        try {
            controller.createExpense(paymentDTO, principal);
        } catch (RuntimeException e) {
            assertTrue(e instanceof RuntimeException);
            Mockito.verify(userEntityService, times(1)).findUserByPrincipal(principal);
        }
    }

    @Test
    public void getExpensesCategories_RequestIsValid_getRequestAccepted() {
        ExpenseType expenseType = buildExpenseType();
        List<ExpenseType> expenseTypeList = Collections.singletonList(expenseType);

        when(expensesService.getExpenseCategories()).thenReturn(expenseTypeList);

        ResponseEntity<?> result = controller.getCategories();

        Mockito.verify(expensesService, times(1)).getExpenseCategories();
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(((List)result.getBody()).size(), expenseTypeList.size());
    }

    @Test
    public void getExpenses_RequestIsValid_getRequestAccepted() {
        UserEntity userEntity = buildUserEntity();
        Optional<UserEntity> appEntityOptional = Optional.of(userEntity);

        ExpensesFilterRequest filterRequest = buildFilterRequest();

        Pageable pageable =
                PageRequest.of(Integer.valueOf(0), Integer.valueOf(20), Sort.by("amount").descending());

        ExpenseDto expenseDto = buildExpenseDto();

        List<ExpenseDto> expenseDtos = Collections.singletonList(expenseDto);

        ExpensesResponseWrapper responseWrapper = ExpensesResponseWrapper.builder()
                .size(20)
                .page(0)
                .data(ExpenseResponseData.builder().items(expenseDtos).build())
                .totalItems(1)
                .totalPages(1)
                .build();

        when(userEntityService.findUserByPrincipal(principal)).thenReturn(appEntityOptional);
        when(expensesService.getExpenses(userEntity, filterRequest, pageable)).thenReturn(responseWrapper);

        ResponseEntity<ExpensesResponseWrapper> result = controller.getAllExpenses("20", "0", null, null, null
                , principal);

        Mockito.verify(userEntityService, times(1)).findUserByPrincipal(principal);
        Mockito.verify(expensesService, times(1)).getExpenses(userEntity, filterRequest, pageable);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(result.getBody().getData().getItems().size(), expenseDtos.size());
    }

    @Test
    public void getExpenses_RequestIsValid_badRequest() {
        Optional<UserEntity> appEntityOptional = Optional.empty();
        when(userEntityService.findUserByPrincipal(principal)).thenReturn(appEntityOptional);
        try {
            controller.getAllExpenses("20", "0", "All", null, null, principal);
        } catch (Exception e) {
            assertTrue(e instanceof RuntimeException);
            Mockito.verify(userEntityService, times(1)).findUserByPrincipal(principal);
        }
    }

    @Test
    public void getExpenseById_RequestIsValid_getRequestAccepted() throws ExpenseNotFoundException {
        ExpenseDto expenseDto = buildExpenseDto();
        when(expensesService.getExpenseByEntityId("")).thenReturn(Optional.of(expenseDto));

        ResponseEntity<ExpenseDto> result = controller.getExpenseById("");

        Mockito.verify(expensesService, times(1)).getExpenseByEntityId("");
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(result.getBody(), expenseDto);
    }

    @Test
    public void deleteExpense_RequestIsValid_expenseNotFound() {
        when(expensesService.getExpenseByEntityId("")).thenReturn(Optional.empty());
        try {
            controller.deleteExpense("");
        } catch (Exception e) {
            assertTrue(e instanceof ExpenseNotFoundException);
            Mockito.verify(expensesService, times(1)).getExpenseByEntityId("");
        }
    }

}
