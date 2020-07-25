package com.arthurtira.tracker.controllers;

import com.arthurtira.tracker.dto.CustomResponse;
import com.arthurtira.tracker.dto.ExpenseDto;
import com.arthurtira.tracker.dto.ExpensesFilterRequest;
import com.arthurtira.tracker.exceptions.ExpenseNotFoundException;
import com.arthurtira.tracker.model.Expense;
import com.arthurtira.tracker.model.UserEntity;
import com.arthurtira.tracker.services.ExpensesService;
import com.arthurtira.tracker.services.UserEntityService;
import com.arthurtira.tracker.wrappers.ExpensesResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping("/expenses")
@Slf4j
public class ExpensesController {

    private ExpensesService expensesService;
    private UserEntityService userEntityService;

    public ExpensesController(ExpensesService expensesService, UserEntityService userEntityService){
        this.expensesService = expensesService;
        this.userEntityService = userEntityService;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ExpensesResponseWrapper> getAllExpenses(@RequestParam(required = false, defaultValue = "20") String size,
                                                                  @RequestParam(required = false, defaultValue = "0") String page,
                                                                  @RequestParam(required = false, defaultValue = "All") String category,
                                                                  @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
                                                                  @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate,
                                                                  Principal principal) {

        Optional<UserEntity> userEntityDtoOptional = this.userEntityService.findUserByPrincipal(principal);
        if(!userEntityDtoOptional.isPresent()) {
            throw new RuntimeException("User not logged in");
        }
        ExpensesFilterRequest filterRequest = ExpensesFilterRequest.builder()
                                            .fromDate(fromDate)
                                            .toDate(toDate)
                                            .category(category)
                                            .build();
        Pageable pageable =
                PageRequest.of(Integer.valueOf(page), Integer.valueOf(size), Sort.by("amount").descending());
        return new ResponseEntity<>(this.expensesService.getExpenses(userEntityDtoOptional.get(), filterRequest, pageable), HttpStatus.OK);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable String expenseId) throws ExpenseNotFoundException {
        Optional<ExpenseDto> optionalExpenseDto = this.expensesService.getExpenseByEntityId(expenseId);
        if(!optionalExpenseDto.isPresent()) {
            throw new ExpenseNotFoundException("Expense with id [ " + expenseId + " ] not found. ");
        }
        return new ResponseEntity<>(optionalExpenseDto.get(), HttpStatus.OK);

    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<CustomResponse> deleteExpense(@PathVariable String expenseId) throws ExpenseNotFoundException {
        Optional<ExpenseDto> optionalExpenseDto = this.expensesService.getExpenseByEntityId(expenseId);
        if(!optionalExpenseDto.isPresent()) {
            throw new ExpenseNotFoundException("Expense with id [ " + expenseId + " ] not found. ");
        }
        this.expensesService.deleteExpense(expenseId);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.NO_CONTENT.value(), "Deleted successfully"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody @Valid ExpenseDto expenseDto, Principal principal) {
        Optional<UserEntity> userEntityDtoOptional = this.userEntityService.findUserByPrincipal(principal);
        if(!userEntityDtoOptional.isPresent()) {
            throw new RuntimeException("User not logged in");
        }
        return new ResponseEntity<>(this.expensesService.createExpense(expenseDto, userEntityDtoOptional.get()), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ExpenseDto> updateExpense(@RequestBody ExpenseDto expenseDto) throws ExpenseNotFoundException {
        Optional<Expense> optionalExpenseDto = this.expensesService.getExpenseById(expenseDto.getExpenseId());
        if(!optionalExpenseDto.isPresent()) {
            throw new ExpenseNotFoundException("Expense with id [ " + expenseDto.getExpenseId() + " ] not found. ");
        }
        return new ResponseEntity<>(this.expensesService.updateExpense(optionalExpenseDto.get(), expenseDto), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        return new ResponseEntity<>(this.expensesService.getExpenseCategories(),  HttpStatus.OK);
    }




}
