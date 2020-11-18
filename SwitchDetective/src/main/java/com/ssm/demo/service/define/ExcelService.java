package com.ssm.demo.service.define;

import com.ssm.demo.vo.DotVO;

import java.sql.Timestamp;
import java.util.List;

public interface ExcelService {
    String createResultTable(List<DotVO> dotVOList, String okPic, Timestamp dateTime, String cabinetId);

    String searchIdentificationRecords(int baseId);

    String searchMistakeRecords(int baseId);
}