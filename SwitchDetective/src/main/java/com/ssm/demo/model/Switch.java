package com.ssm.demo.model;

public class Switch {
    private String cabinetId;
    private String name;
    private String standardState;
    private String note;
    private int row;
    private int column;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

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

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStandardState() {
        return standardState;
    }

    public void setStandardState(String standardState) {
        this.standardState = standardState;
    }

    public String toString(){
        return "name: " + name + ",cabinetId:" + cabinetId + ",standardState:" + standardState;
    }

}
