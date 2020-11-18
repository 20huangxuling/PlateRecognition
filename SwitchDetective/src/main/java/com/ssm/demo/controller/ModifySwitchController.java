package com.ssm.demo.controller;

import com.ssm.demo.mapper.SwitchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ModifySwitchController {

    private SwitchMapper switchMapper;

    @Autowired
    public void setSwitchMapper(SwitchMapper switchMapper) {
        this.switchMapper = switchMapper;
    }

    @PostMapping("/modifySwitch")
    public ModelAndView modifySwitch(MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

}
