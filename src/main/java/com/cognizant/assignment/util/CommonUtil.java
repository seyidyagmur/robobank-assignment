package com.cognizant.assignment.util;

import com.cognizant.assignment.dto.StatementRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommonUtil {

    public static List<StatementRecord> getErrorEndBalanceRecords(List<StatementRecord> list) {
       return list.stream().filter(predicateErrorEndBalance()).collect(Collectors.toList());
    }

    private static Predicate<StatementRecord> predicateErrorEndBalance()
    {
        return p-> !p.getStartBalance().add( p.getMutation()).equals(p.getEndBalance());
    }
    public static List<StatementRecord> getDublicateReferenceRecords(List<StatementRecord> list)
    {
        List<StatementRecord> filteredList=new ArrayList<>();
        list.stream().collect(Collectors.groupingBy(StatementRecord::getReference)).forEach((k,v)->{
            if (v.size()>1)
                filteredList.addAll(v);

        });
        return filteredList;
    }
}
