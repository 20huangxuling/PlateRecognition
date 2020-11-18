package com.ssm.demo.mapper;

import com.ssm.demo.model.ResultExcelRow;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllRecordRowMapper {
    List<ResultExcelRow> searchAllRecords(int baseId);

    List<ResultExcelRow> searchAllMistake(int baseId);
}
