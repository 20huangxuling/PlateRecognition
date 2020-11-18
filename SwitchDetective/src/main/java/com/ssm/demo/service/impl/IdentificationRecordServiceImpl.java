package com.ssm.demo.service.impl;

import com.ssm.demo.mapper.IdentificationRecordMapper;
import com.ssm.demo.model.IdentificationRecord;
import com.ssm.demo.service.define.IdentificationRecordService;
import com.ssm.demo.vo.Dot;
import com.ssm.demo.vo.DotVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class IdentificationRecordServiceImpl implements IdentificationRecordService {

    private IdentificationRecordMapper mapper;

    @Autowired
    public void setMapper(IdentificationRecordMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public IdentificationRecord getIdentificationRecord(long id) {
        return mapper.getIdentificationRecord(id);
    }

    @Override
    public long insertIdentificationRecord(IdentificationRecord record) {
        return mapper.insertIdentificationRecord(record);
    }

    @Override
    public void insertToDataBase(ArrayList<Dot> dots, String okPic, String cabinetId, Timestamp date) {
        IdentificationRecord record = new IdentificationRecord();
        for (Dot dot : dots) {
            record.setCurState(dot.getState());
            record.setDate(date);
            record.setOkPic(okPic);
            record.setCabinetId(cabinetId);
            record.setRow(dot.getRow());
            record.setColumn(dot.getColumn());
            insertIdentificationRecord(record);
        }
    }

    @Override
    public void insertToDataBase(List<DotVO> dots, String okPic, String cabinetId, Timestamp date) {
        IdentificationRecord record = new IdentificationRecord();
        Dot dot = null;
        for (DotVO dotVO : dots) {
            dot = dotVO.getDot();
            record.setCurState(dot.getState());
            record.setDate(date);
            record.setOkPic(okPic);
            record.setCabinetId(cabinetId);
            record.setRow(dot.getRow());
            record.setColumn(dot.getColumn());
            insertIdentificationRecord(record);
        }
    }
}
