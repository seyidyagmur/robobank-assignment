package com.cognizant.assignment.config;

import com.cognizant.assignment.dto.CustomResponse;
import com.cognizant.assignment.dto.Status;
import com.cognizant.assignment.exception.InternalServerException;
import com.cognizant.assignment.exception.InvalidStatementException;
import com.cognizant.assignment.exception.JsonParsingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(InvalidStatementException.class)
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse handleInvalidStatementException(InvalidStatementException exception) {
        return new CustomResponse(exception.getStatus(), exception.getRecordList());
    }
    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomResponse handleInternalServerException(InternalServerException exception) {
        return new CustomResponse(Status.INTERNAL_SERVER_ERROR, Collections.emptyList());
    }
    @ExceptionHandler(JsonParsingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleJsonParsingException(JsonParsingException exception) {
        return new CustomResponse(Status.BAD_REQUEST, Collections.emptyList());
    }
}
