package com.arthurtira.tracker.mappers;

import com.arthurtira.tracker.dto.ExpenseDto;
import com.arthurtira.tracker.model.Expense;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MapperConfig.class})
public class ExpensesMapperTests {

    @Autowired
    ExpensesMapper mapper;

    @Test
    public void fromDTO() {
        Expense expense = buildExpense();

        ExpenseDto expenseDto  = buildExpenseDto();

        //assertThat(mapper.fromDto(expenseDto)).isEqualToIgnoringGivenFields(expense, "createdOn, entityId");

    }

    private ExpenseDto buildExpenseDto() {
        return ExpenseDto.builder()
                .expenseDate(new Date())
                .currency("USD")
                .description("description")
                .comment("comment")
                .amount(BigDecimal.TEN)
                .category("HOUSEHOLD")
                .build();
    }

    private Expense buildExpense() {
        Expense expense = new Expense();
        expense.setDescription("description");
        expense.setComment("comment");
        expense.setCategory("HOUSEHOLD");
        expense.setAmount(BigDecimal.TEN);
        expense.setCreatedOn(new Date());

        return expense;
    }

}
