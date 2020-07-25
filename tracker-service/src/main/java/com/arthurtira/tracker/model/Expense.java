package com.arthurtira.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Expense extends AbstractEntity {

    @NotNull
    private String description;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String comment;
    @NotNull
    private String category;
    @OneToOne
    private UserEntity userEntity;


}
