package com.ssm.demo.mapper;

import com.ssm.demo.model.Switch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwitchMapper {
    List<Switch> searchSwitchesCabinetId(@Param("cabinetId") String cabinetId);

    Switch getSwitch(String cabinetId, int row, int column);

    void insertSwitch(Switch swt);
}
