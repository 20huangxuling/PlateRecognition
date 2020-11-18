package com.ssm.demo.model;


import java.sql.Date;
import java.sql.Timestamp;

public class IdentificationRecord {
    private Timestamp date;
    private long id;
    private String okPic;
    private String curState;
    private String cabinetId;
    private int row;
    private int column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOkPic() {
        return okPic;
    }

    public void setOkPic(String okPic) {
        this.okPic = okPic;
    }

    public String getCurState() {
        return curState;
    }

    public void setCurState(String curState) {
        this.curState = curState;
    }

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }

    public String toString(){
        return String.format("id: %d,switch id: %d, curState: %d, okPicPath: %s",id, cabinetId,curState, okPic);
    }
}
