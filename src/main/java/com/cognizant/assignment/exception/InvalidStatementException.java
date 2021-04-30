package com.cognizant.assignment.exception;

import com.cognizant.assignment.dto.StatementRecord;
import com.cognizant.assignment.dto.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class InvalidStatementException extends Exception{
private Status status;
private List<StatementRecord> recordList;
}
