package com.ssm.demo.service.impl;

import com.ssm.demo.mapper.BaseStationMapper;
import com.ssm.demo.mapper.CabinetMapper;
import com.ssm.demo.service.define.ResultTableService;
import com.ssm.demo.service.define.SwitchListService;
import com.ssm.demo.vo.Dot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultTableServiceImpl implements ResultTableService {

    private SwitchListService switchListService;
    private CabinetMapper cabinetMapper;
    private BaseStationMapper stationMapper;

    @Autowired
    public void setSwitchListService(SwitchListService switchListService) {
        this.switchListService = switchListService;
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
    public void CreateResultTable(List<Dot> dots, String okPic, String cabinetId) {

    }
}
