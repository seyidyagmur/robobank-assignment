package com.cognizant.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CustomResponse {
    private Status result;
    private List<StatementRecord> errorRecords;
}
