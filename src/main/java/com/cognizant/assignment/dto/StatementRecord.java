package com.cognizant.assignment.dto;

import lombok.*;

import java.math.BigDecimal;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatementRecord {
    private long reference;
    private String accountNumber;
    private BigDecimal startBalance;
    private BigDecimal mutation;
    private String description;
    private BigDecimal endBalance;
}
