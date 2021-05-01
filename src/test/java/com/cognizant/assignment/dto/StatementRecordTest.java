package com.cognizant.assignment.dto;

import com.cognizant.assignment.model.StatementRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StatementRecordTest {

    @Test
    public void showTwoRecordsAreEquals(){
        StatementRequest request1=StatementRequest.builder().accountNumber("123").reference("123").endBalance("100").build();
        StatementRequest request2=StatementRequest.builder().accountNumber("123").reference("123").endBalance("100").build();
        assertEquals(request1,request2);
    }
    @Test
    public void showTwoRecordsAreNotEquals(){
        StatementRequest request1=StatementRequest.builder().accountNumber("123").reference("123").endBalance("100").build();
        StatementRequest request2=StatementRequest.builder().accountNumber("1234").reference("123").endBalance("100").build();
        assertNotEquals(request1,request2);
    }
}
