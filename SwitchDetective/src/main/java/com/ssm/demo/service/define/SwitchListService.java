package com.ssm.demo.service.define;

import com.ssm.demo.model.Switch;
import java.util.List;

public interface SwitchListService {
    List<Switch> searchSwitch(String cabinetId);

    void insertSwitch(Switch swt);
}
