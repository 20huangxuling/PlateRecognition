package com.ssm.demo.params;

import com.ssm.demo.vo.Dot;

import java.util.List;

public class DotDetecteds_okPic {
    private List<Dot> dots;
    private String okPic;

    public DotDetecteds_okPic(List<Dot> dots, String okPic) {
        this.dots = dots;
        this.okPic = okPic;
    }

    public List<Dot> getDots() {
        return dots;
    }

    public void setDots(List<Dot> dots) {
        this.dots = dots;
    }

    public String getOkPic() {
        return okPic;
    }

    public void setOkPic(String okPic) {
        this.okPic = okPic;
    }
}
