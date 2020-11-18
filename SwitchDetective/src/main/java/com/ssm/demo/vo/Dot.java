package com.ssm.demo.vo;

public class Dot {
    private String state;
    private float score;
    private int x;
    private int y;
    private int row;
    private int column;

    public Dot(String state, float score, int x, int y) {
        this.state = state;
        this.score = score;
        this.x = x;
        this.y = y;
    }

    public Dot(String state, float score, int x, int y, int row, int column) {
        this.state = state;
        this.score = score;
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
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

    public String getState() {
        return state;
    }

    public float getScore() {
        return score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString(){
        return String.format("state: %-8s, Score: %-8f, X: %-8d, Y: %-8d",getState(),getScore(),getX(),getY());
    }
}