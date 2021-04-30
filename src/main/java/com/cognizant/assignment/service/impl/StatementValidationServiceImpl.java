package com.cognizant.assignment.service.impl;

import com.cognizant.assignment.exception.InternalServerException;
import com.cognizant.assignment.exception.InvalidStatementException;
import com.cognizant.assignment.dto.CustomResponse;
import com.cognizant.assignment.dto.StatementRecord;
import com.cognizant.assignment.dto.Status;
import com.cognizant.assignment.exception.JsonParsingException;
import com.cognizant.assignment.model.StatementRequest;
import com.cognizant.assignment.service.StatementValidationService;
import com.cognizant.assignment.util.CommonUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StatementValidationServiceImpl implements StatementValidationService {

    @SneakyThrows
    @Override
    public CustomResponse validate(List<StatementRequest> statementRequestList)  {
        if (statementRequestList!=null && statementRequestList.isEmpty()){
            throw new InternalServerException("Record list must be more than 0");
        }
        List<StatementRecord> list =null;
        try {
            list = getMappepList(statementRequestList);
        }catch (Exception ex){
            throw new JsonParsingException("JSON Parsing Exception occured");
        }
        List<StatementRecord> dublicateReferencedRecords = CommonUtil.getDublicateReferenceRecords(list);
        List<StatementRecord> errorEndBalanceRecords = CommonUtil.getErrorEndBalanceRecords(list);
        if(!dublicateReferencedRecords.isEmpty() && !errorEndBalanceRecords.isEmpty()){
            throw new InvalidStatementException(Status.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE,
                    Stream.of(dublicateReferencedRecords,errorEndBalanceRecords).flatMap(x -> x.stream())
                            .collect(Collectors.toList()));
        }else if(!dublicateReferencedRecords.isEmpty()){
            throw new InvalidStatementException(Status.DUBLICATE_REFERENCE,dublicateReferencedRecords);
        }else if(!errorEndBalanceRecords.isEmpty()){
            throw new InvalidStatementException(Status.INCORRECT_END_BALANCE,errorEndBalanceRecords);
        }

    return new CustomResponse(Status.SUCCESS, Collections.emptyList());
    }
    private List<StatementRecord> getMappepList(List<StatementRequest> list){
        return list.stream()
                .map(r->
                        new StatementRecord(Long.valueOf(r.getReference()),
                                r.getAccountNumber(),
                                new BigDecimal(r.getStartBalance()),
                                new BigDecimal(r.getMutation()),
                                r.getDescription(),
                                new BigDecimal(r.getEndBalance())
                        )).collect(Collectors.toList());
    }
}
