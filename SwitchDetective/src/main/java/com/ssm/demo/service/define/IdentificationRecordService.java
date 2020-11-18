package com.ssm.demo.service.define;

import com.ssm.demo.model.IdentificationRecord;
import com.ssm.demo.vo.Dot;
import com.ssm.demo.vo.DotVO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public interface IdentificationRecordService {
    IdentificationRecord getIdentificationRecord(long id);

    long insertIdentificationRecord(IdentificationRecord record);

    void insertToDataBase(ArrayList<Dot> dots, String okPic, String cabinetId, Timestamp date);

    void insertToDataBase(List<DotVO> dots, String okPic, String cabinetId, Timestamp date);
}
