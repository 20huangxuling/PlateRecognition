package com.ssm.demo.mapper;

import com.ssm.demo.model.BaseStation;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseStationMapper {
    BaseStation getStationById(int id);

    BaseStation getStationByCabinetID(String cabinetId);
}
