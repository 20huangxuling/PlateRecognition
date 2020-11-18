package com.ssm.demo.mapper;

import com.ssm.demo.model.Cabinet;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinetMapper {
    Cabinet getCabinetById(String id);
}
