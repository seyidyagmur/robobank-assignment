package com.cognizant.assignment.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatementRequest {
    private String reference;
    private String accountNumber;
    private String startBalance;
    private String mutation;
    private String description;
    private String endBalance;
}
