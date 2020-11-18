package com.ssm.demo.service.impl;

import com.ssm.demo.mapper.AllRecordRowMapper;
import com.ssm.demo.mapper.BaseStationMapper;
import com.ssm.demo.mapper.CabinetMapper;
import com.ssm.demo.model.BaseStation;
import com.ssm.demo.model.Cabinet;
import com.ssm.demo.model.ResultExcelRow;
import com.ssm.demo.model.Switch;
import com.ssm.demo.service.define.ExcelService;
import com.ssm.demo.util.ExcelUtil;
import com.ssm.demo.util.PathUtil;
import com.ssm.demo.vo.Dot;
import com.ssm.demo.vo.DotVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    private String excelPath_result = PathUtil.directory_excel_resultRecord;
    private String excelPath_mistake = PathUtil.directory_excel_mistakeRecord;
    private String excelPath_result_http = PathUtil.url_downloadExcel_resultRecord;
    private String excelPath_mistake_http = PathUtil.url_downloadExcel_mistakeRecord;
    private BaseStationMapper stationMapper;
    private CabinetMapper cabinetMapper;
    private AllRecordRowMapper recordRowMapper;

    @Autowired
    public void setRecordRowMapper(AllRecordRowMapper recordRowMapper) {
        this.recordRowMapper = recordRowMapper;
    }

    @Autowired
    public void setCabinetMapper(CabinetMapper cabinetMapper) {
        this.cabinetMapper = cabinetMapper;
    }

    @Autowired
    public void setStationMapper(BaseStationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    @Override
    public String createResultTable(List<DotVO> dotVOList, String okPic, Timestamp dateTime, String cabinetId) {
        BaseStation station = stationMapper.getStationByCabinetID(cabinetId);
        Cabinet cabinet = cabinetMapper.getCabinetById(cabinetId);
        if (station == null || cabinet == null) {
            return null;
        }
        String[] titles = new String[]{"站名", "屏柜编号", "屏柜名称", "压板编号", "压板名称", "原始库状态", "识别结果", "照片链接", "识别时间"};
        ResultExcelRow row = new ResultExcelRow();
        Dot dotDetected = null;
        Switch swt = null;
        ArrayList<String[]> values = new ArrayList<>();
        for (DotVO dot : dotVOList) {
            dotDetected = dot.getDot();
            swt = dot.getaSwitch();
            row.setBaseStation(station.getName());
            row.setCabinetId(cabinetId);
            row.setCabinetName(cabinet.getName());
            row.setSwitchId(swt.getId());
            row.setSwitchName(swt.getName());
            row.setStandardState(swt.getStandardState());
            row.setResultState(dotDetected.getState());
            row.setOkPic(okPic);
            row.setTime(dateTime);
            row.setBaseStation(station.getName());
            values.add(row.totStrings());
        }
        String path = excelPath_result + cabinet.getName() + ".xls";
        String httpPath = excelPath_result_http + cabinet.getName() + ".xls";
        try {
            ExcelUtil.createTable(path, "result", titles, values);
        } catch (IOException e) {
            httpPath = null;
        }
        return httpPath;
    }

    @Override
    public String searchIdentificationRecords(int baseId) {
        List<ResultExcelRow> resultExcelRows = recordRowMapper.searchAllRecords(baseId);
        if (resultExcelRows == null || resultExcelRows.isEmpty()) {
            return null;
        }
        String path = excelPath_result + "allRecords.xls";
        String httpPath = excelPath_result_http + "allRecords.xls";
        ArrayList<String[]> values = new ArrayList<>();
        for (ResultExcelRow row : resultExcelRows) {
            values.add(row.totStrings());
        }
        String[] titles = new String[]{"站名", "屏柜编号", "屏柜名称", "压板编号", "压板名称", "原始库状态", "识别结果", "照片链接", "识别时间"};
        try {
            ExcelUtil.createTable(path, "allRecords", titles, values);
        } catch (IOException e) {
            httpPath = null;
            e.printStackTrace();
        }
        return httpPath;
    }

    @Override
    public String searchMistakeRecords(int baseId) {
        List<ResultExcelRow> resultExcelRows = recordRowMapper.searchAllMistake(baseId);
        String path = excelPath_mistake + "mistakeRecords.xls";
        String httpPath = excelPath_mistake_http + "mistakeRecords.xls";
        ArrayList<String[]> values = new ArrayList<>();
        for (ResultExcelRow row : resultExcelRows) {
            values.add(row.totStrings());
        }
        String[] titles = new String[]{"站名", "屏柜编号", "屏柜名称", "压板编号", "压板名称", "原始库状态", "识别结果", "照片链接", "识别时间"};
        try {
            ExcelUtil.createTable(path, "allRecords", titles, values);
        } catch (IOException e) {
            httpPath = null;
            e.printStackTrace();
        }
        return httpPath;
    }
}
