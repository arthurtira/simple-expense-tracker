package com.arthurtira.tracker.mappers;


import com.arthurtira.tracker.dto.ExpenseDto;
import com.arthurtira.tracker.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface ExpensesMapper {

    @Mappings({
            @Mapping(source = "entityId" , target = "expenseId"),
            @Mapping( source = "createdOn" , target = "expenseDate")
    })
    ExpenseDto toDto(Expense expense);
    Expense fromDto(ExpenseDto expenseDto);
    Stream<ExpenseDto> toDtoStream(Stream<Expense> expenseStream);
}
