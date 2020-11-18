package com.ssm.demo.service.impl;

import com.ssm.demo.mapper.SwitchMapper;
import com.ssm.demo.model.Switch;
import com.ssm.demo.service.define.SwitchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SwitchListServiceImpl implements SwitchListService {

    private SwitchMapper mapper;

    @Autowired
    public void setMapper(SwitchMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Switch> searchSwitch(String cabinetId) {
        return mapper.searchSwitchesCabinetId(cabinetId);
    }

    @Override
    public void insertSwitch(Switch swt) {
        mapper.insertSwitch(swt);
    }
}
