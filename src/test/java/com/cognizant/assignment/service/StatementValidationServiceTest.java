package com.cognizant.assignment.service;

import com.cognizant.assignment.exception.InternalServerException;
import com.cognizant.assignment.exception.JsonParsingException;
import com.cognizant.assignment.model.StatementRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StatementValidationServiceTest {

    private static String IBAN="AL47212110090000000235698741";
    private static String DESCRIPTION="description";
    @Autowired
    private StatementValidationService statementValidationService;

    @Test
    public void testInternalServerErrorOccured(){
        InternalServerException exception=  Assertions.assertThrows(InternalServerException.class,()->statementValidationService.validate(Collections.emptyList()));
        assertEquals(exception.getMessage(),"Record list must be more than 0");
    }
    @Test
    public void testJsonParsingExceptionOccured(){
        List<StatementRequest> list=List.of(
                StatementRequest.builder()
                        .accountNumber(IBAN).description(DESCRIPTION).reference("123")
                        .startBalance("100abc").mutation("-10").endBalance("90").build()
        );
        JsonParsingException exception= Assertions.assertThrows(JsonParsingException.class,()->statementValidationService.validate(list));
        assertEquals(exception.getMessage(),"JSON Parsing Exception occured");

    }

}
