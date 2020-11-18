package com.ssm.demo.vo;

import com.ssm.demo.model.Switch;

public class DotVO {
    private Dot dot;
    private boolean isCorrect;
    private Switch aSwitch;

    public DotVO(Dot dot, Switch aSwitch, boolean isCorrect) {
        this.dot = dot;
        this.isCorrect = isCorrect;
        this.aSwitch = aSwitch;
    }

    public Dot getDot() {
        return dot;
    }

    public void setDot(Dot dot) {
        this.dot = dot;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Switch getaSwitch() {
        return aSwitch;
    }

    public void setaSwitch(Switch aSwitch) {
        this.aSwitch = aSwitch;
    }
}
