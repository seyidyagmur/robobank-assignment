package com.cognizant.assignment.service;

import com.cognizant.assignment.dto.CustomResponse;
import com.cognizant.assignment.model.StatementRequest;

import java.util.List;

public interface StatementValidationService {
    CustomResponse validate(List<StatementRequest> list);
}
