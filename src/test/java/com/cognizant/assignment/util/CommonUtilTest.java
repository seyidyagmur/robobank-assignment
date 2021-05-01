package com.cognizant.assignment.util;

import com.cognizant.assignment.dto.StatementRecord;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CommonUtilTest {

    @Test
    public void testErrorRecordsGetsRightRecordCount(){
        List<StatementRecord> statementRecordList=
                List.of(
                        StatementRecord.builder().startBalance(new BigDecimal("100"))
                                .mutation(new BigDecimal("-20"))
                                .endBalance(new BigDecimal("70")).build(),
                        StatementRecord.builder().startBalance(new BigDecimal("100"))
                                .mutation(new BigDecimal("-20"))
                                .endBalance(new BigDecimal("80")).build(),
                        StatementRecord.builder().startBalance(new BigDecimal("100"))
                                .mutation(new BigDecimal("-20"))
                                .endBalance(new BigDecimal("80")).build()
                );
        assertEquals(statementRecordList.size(),3);
        statementRecordList= CommonUtil.getErrorEndBalanceRecords(statementRecordList);
        assertNotNull(statementRecordList);
        assertEquals(statementRecordList.size(),1);
    }

    @Test
    public void testDublicateReferenceError(){
        List<StatementRecord> statementRecordList=
                List.of(
                        StatementRecord.builder().reference(1234).build(),
                        StatementRecord.builder().reference(1234).build(),
                        StatementRecord.builder().reference(1555).build()
                        );
        assertEquals(statementRecordList.size(),3);
        statementRecordList= CommonUtil.getDublicateReferenceRecords(statementRecordList);
        assertNotNull(statementRecordList);
        assertEquals(statementRecordList.size(),2);
    }
}
