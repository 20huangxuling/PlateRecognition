package com.ssm.demo.vo;

import java.sql.Timestamp;
import java.util.List;

public class OneDetected {
    private List<DotVO> results;
    private Timestamp dateTime;
    private String path_Image_Detected;
    private String cabinetId;

    public OneDetected(List<DotVO> results, Timestamp dateTime, String path_Image_Detected, String cabinetId) {
        this.results = results;
        this.dateTime = dateTime;
        this.path_Image_Detected = path_Image_Detected;
        this.cabinetId = cabinetId;
    }

    public List<DotVO> getResults() {
        return results;
    }

    public void setResults(List<DotVO> results) {
        this.results = results;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getPath_Image_Detected() {
        return path_Image_Detected;
    }

    public void setPath_Image_Detected(String path_Image_Detected) {
        this.path_Image_Detected = path_Image_Detected;
    }

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }
}
