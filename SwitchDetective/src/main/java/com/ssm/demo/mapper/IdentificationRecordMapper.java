package com.ssm.demo.mapper;

import com.ssm.demo.model.IdentificationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentificationRecordMapper {
    IdentificationRecord getIdentificationRecord(@Param("identificationId") Long id);
    long insertIdentificationRecord(IdentificationRecord record);

}
