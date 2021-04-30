package com.cognizant.assignment.controller;

import com.cognizant.assignment.model.StatementRequest;
import com.cognizant.assignment.service.StatementValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StatementValidationController {
    @Autowired
    private StatementValidationService statementValidationService;

    @PostMapping(value = "/validate")
    private ResponseEntity validate(@RequestBody List<StatementRequest> list){
        return ResponseEntity.ok(statementValidationService.validate(list));
    }

}
