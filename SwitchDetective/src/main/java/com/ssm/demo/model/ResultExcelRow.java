package com.ssm.demo.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ResultExcelRow {
    private String baseStation;
    private String cabinetId;
    private String cabinetName;
    private String switchId;
    private String switchName;
    private String standardState;
    private String resultState;
    private String okPic;
    private Timestamp time;

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public String getBaseStation() {
        return baseStation;
    }

    public void setBaseStation(String baseStation) {
        this.baseStation = baseStation;
    }

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getSwitchId() {
        return switchId;
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
    }

    public String getStandardState() {
        return standardState;
    }

    public void setStandardState(String standardState) {
        this.standardState = standardState;
    }

    public String getResultState() {
        return resultState;
    }

    public void setResultState(String resultState) {
        this.resultState = resultState;
    }

    public String getOkPic() {
        return okPic;
    }

    public void setOkPic(String okPic) {
        this.okPic = okPic;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String[] totStrings() {
        return new String[]{baseStation, cabinetId, cabinetName, switchId, switchName, standardState, resultState, okPic, time.toString()};
    }
}
