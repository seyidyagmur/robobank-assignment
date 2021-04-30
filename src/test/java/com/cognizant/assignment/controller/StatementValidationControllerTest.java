package com.cognizant.assignment.controller;

import com.cognizant.assignment.dto.CustomResponse;
import com.cognizant.assignment.dto.Status;
import com.cognizant.assignment.model.StatementRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatementValidationControllerTest {
    private static String IBAN="AL47212110090000000235698741";
    private static String DESCRIPTION="description";
    @LocalServerPort
    private int port;
    protected String url;

    @Autowired
    protected TestRestTemplate restTemplate;
    @Before
    public void setUp(){
        this.url = "http://localhost:" + port + "/api/v1/validate";
    }

    @Test
    public void testStatementValidatedSuccessfully(){
        StatementRequest statementRequest=  StatementRequest.builder()
                .accountNumber(IBAN).description(DESCRIPTION).reference("12345")
                .startBalance("120").mutation("-20").endBalance("100").build();
        ResponseEntity<CustomResponse>  customResponse =
                restTemplate.postForEntity(url, List.of(statementRequest), CustomResponse.class);
        assertEquals(customResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(customResponse.getBody().getResult(), Status.SUCCESS);
        assertEquals(customResponse.getBody().getErrorRecords().size(), 0);
    }
    @Test
    public void testEndBalanceIsIncorrect(){
        StatementRequest statementRequest1=  StatementRequest.builder()
                .accountNumber(IBAN).description(DESCRIPTION).reference("12345")
                .startBalance("120").mutation("-20").endBalance("110").build();

        ResponseEntity<CustomResponse>  customResponse =
                restTemplate.postForEntity(url, List.of(statementRequest1), CustomResponse.class);
        assertEquals(customResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(customResponse.getBody().getResult(), Status.INCORRECT_END_BALANCE);
        assertEquals(customResponse.getBody().getErrorRecords().size(), 1);
    }
    @Test
    public void testDublicateReferenceError(){
        StatementRequest statementRequest1=  StatementRequest.builder()
                .accountNumber(IBAN).description(DESCRIPTION).reference("12345")
                .startBalance("120").mutation("-20").endBalance("100").build();
        StatementRequest statementRequest2=  StatementRequest.builder()
                .accountNumber(IBAN).description(DESCRIPTION).reference("12345")
                .startBalance("120").mutation("-20").endBalance("100").build();

        ResponseEntity<CustomResponse>  customResponse =
                restTemplate.postForEntity(url, List.of(statementRequest1,statementRequest2), CustomResponse.class);
        assertEquals(customResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(customResponse.getBody().getResult(), Status.DUBLICATE_REFERENCE);
        assertEquals(customResponse.getBody().getErrorRecords().size(), 2);
    }
    @Test
    public void testDublicateReferenceAndIncorrectEndBalanceError(){
        StatementRequest statementRequest1=  StatementRequest.builder()
                .accountNumber(IBAN).description(DESCRIPTION).reference("12345")
                .startBalance("120").mutation("-20").endBalance("110").build();
        StatementRequest statementRequest2=  StatementRequest.builder()
                .accountNumber(IBAN).description(DESCRIPTION).reference("12345")
                .startBalance("120").mutation("-20").endBalance("100").build();

        ResponseEntity<CustomResponse>  customResponse =
                restTemplate.postForEntity(url, List.of(statementRequest1,statementRequest2), CustomResponse.class);
        assertEquals(customResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(customResponse.getBody().getResult(), Status.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
        assertEquals(customResponse.getBody().getErrorRecords().size(), 3);
    }
    @Test
    public void testJsonParsingExceptionOccured(){
        String incorrectReference="ABC";
        StatementRequest statementRequest1=  StatementRequest.builder()
                .accountNumber(IBAN).description(DESCRIPTION).reference(incorrectReference)
                .startBalance("120").mutation("-20").endBalance("110").build();
        StatementRequest statementRequest2=  StatementRequest.builder()
                .accountNumber(IBAN).description(DESCRIPTION).reference("12345")
                .startBalance("120").mutation("-20").endBalance("100").build();

        ResponseEntity<CustomResponse>  customResponse =
                restTemplate.postForEntity(url, List.of(statementRequest1,statementRequest2), CustomResponse.class);
        assertEquals(customResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(customResponse.getBody().getResult(), Status.BAD_REQUEST);
        assertEquals(customResponse.getBody().getErrorRecords().size(), 0);
    }
    @Test
    public void testInternalServerErrorOccured(){

        ResponseEntity<CustomResponse>  customResponse =
                restTemplate.postForEntity(url, Collections.emptyList(), CustomResponse.class);
        assertEquals(customResponse.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(customResponse.getBody().getResult(), Status.INTERNAL_SERVER_ERROR);
        assertEquals(customResponse.getBody().getErrorRecords().size(), 0);
    }

}
