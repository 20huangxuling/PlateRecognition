package com.ssm.demo.service.define;

import com.ssm.demo.vo.Dot;

import java.util.List;

public interface ResultTableService {
    void CreateResultTable(List<Dot> dots, String okPic, String cabinetId);
}
